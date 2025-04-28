package com.db.desafio_naruto.application.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.BatalhaUseCase;
import com.db.desafio_naruto.application.port.in.dto.batalha.AcaoBatalhaRequestDTO;
import com.db.desafio_naruto.application.port.in.dto.batalha.BatalhaResponseDTO;
import com.db.desafio_naruto.application.port.in.dto.batalha.IniciarBatalhaRequestDTO;
import com.db.desafio_naruto.application.port.in.dto.personagem.PersonagemDTO;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.application.port.out.SalvarBatalhaPort;
import com.db.desafio_naruto.application.service.exceptions.BatalhaNaoEncontradaException;
import com.db.desafio_naruto.application.service.exceptions.JutsuNaoEncontradoException;
import com.db.desafio_naruto.application.service.exceptions.PersonagemNaoEncontradoException;
import com.db.desafio_naruto.application.service.exceptions.TurnoInvalidoException;
import com.db.desafio_naruto.domain.model.Batalha;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.Batalha.AtaquePendente;
import com.db.desafio_naruto.domain.model.enums.TipoAcao;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.PersonagemMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BatalhaService implements BatalhaUseCase {
    private final BuscarPorIdPersonagemPort personagemPort;
    private final SalvarBatalhaPort batalhaPort;
    private final LogPort logPort;
    private final PersonagemMapper personagemMapper;
    private final Random random = new Random();

    public BatalhaService(
            BuscarPorIdPersonagemPort personagemPort,
            SalvarBatalhaPort batalhaPort,
            LogPort logPort, PersonagemMapper personagemMapper) {
        this.personagemPort = personagemPort;
        this.batalhaPort = batalhaPort;
        this.logPort = logPort;
        this.personagemMapper = personagemMapper;
    }

    @Override
    public BatalhaResponseDTO iniciarBatalha(IniciarBatalhaRequestDTO request) {
        Personagem ninja1 = personagemPort.buscarPorId(request.getNinjaDesafianteId())
            .orElseThrow(() -> new PersonagemNaoEncontradoException(request.getNinjaDesafianteId()));
        Personagem ninja2 = personagemPort.buscarPorId(request.getNinjaDesafiadoId())
            .orElseThrow(() -> new PersonagemNaoEncontradoException(request.getNinjaDesafiadoId()));

        Batalha batalha = new Batalha(null, ninja1, ninja2, false, 1, ninja1.getId());
        batalha = batalhaPort.salvar(batalha);

        String mensagem = "Batalha iniciada! " + ninja1.getNome() + " vs " + ninja2.getNome();

        return criarBatalhaResponse(batalha, mensagem);
    }

    @Override
    public BatalhaResponseDTO executarAcao(Long batalhaId, AcaoBatalhaRequestDTO request) {
        Batalha batalha = batalhaPort.buscarPorId(batalhaId)
            .orElseThrow(() -> new BatalhaNaoEncontradaException(batalhaId));

        if (batalha.isFinalizada()) {
            String vencedor = determinarVencedor(batalha);
            String mensagem = "Batalha finalizada! Vencedor: " + vencedor;
            
            return criarBatalhaResponse(batalha, mensagem);
        }

        if (!batalha.podeJogar(request.ninjaId())) {
            throw new TurnoInvalidoException();
        }

        batalha.getNinja1().setChakra(batalha.getChackraNinja1());
        batalha.getNinja2().setChakra(batalha.getChackraNinja2());


        BatalhaResponseDTO resultado = processarAcao(batalha, request);
        
        if (batalha.temAtaquePendente()) {
            logPort.debug("Ataque pendente: {}", batalha.getAtaquePendente());
        }
        
        batalha = batalhaPort.salvar(batalha);

        if (batalha.batalhaTerminou()) {
            batalha.setFinalizada(true);
            batalha = batalhaPort.salvar(batalha);
            return resultado;
        }

        batalha.proximoTurno();
        batalha = batalhaPort.salvar(batalha);

        return resultado;
    }

    @Override
    public BatalhaResponseDTO consultarBatalha(Long batalhaId) {
        Batalha batalha = batalhaPort.buscarPorId(batalhaId)
            .orElseThrow(() -> new BatalhaNaoEncontradaException(batalhaId));

        String mensagem;
        if (batalha.isFinalizada()) {
            String vencedor = batalha.getVidaNinja1() <= 0 ? batalha.getNinja2().getNome() : batalha.getNinja1().getNome();
            mensagem = "Batalha finalizada! Vencedor: " + vencedor;
        } else if (batalha.temAtaquePendente()) {
            final AtaquePendente ataque = batalha.getAtaquePendente();
            final Personagem atacante = batalha.getNinja1()
                    .getId().equals(ataque.ninjaAtacanteId()) ? batalha.getNinja1() : batalha.getNinja2();
            final Personagem defensor = batalha.getNinja1()
                    .getId().equals(ataque.ninjaAtacanteId()) ? batalha.getNinja2() : batalha.getNinja1();
            
            mensagem = gerarMensagemDeAtaque(atacante, defensor, ataque);
        } else {
            mensagem = gerarMensagemDeRodada(batalha);
        }

        return criarBatalhaResponse(batalha, mensagem);
    }

    private BatalhaResponseDTO processarAcao(Batalha batalha, AcaoBatalhaRequestDTO acao) {
        Personagem ninjaAtual = batalha.getNinjaAtual().equals(batalha.getNinja1().getId()) 
            ? batalha.getNinja1() 
            : batalha.getNinja2();
            
        Personagem outroNinja = batalha.getNinjaAtual().equals(batalha.getNinja1().getId()) 
            ? batalha.getNinja2() 
            : batalha.getNinja1();
    
        String mensagem;
        if (acao.tipoAcao() == TipoAcao.USAR_JUTSU) {
            mensagem = processarJutsu(batalha, ninjaAtual, outroNinja, acao.nomeJutsu());
        } else if (acao.tipoAcao() == TipoAcao.DESVIAR) {
            mensagem = processarDesvio(batalha, outroNinja, ninjaAtual);
        } else {
            throw new IllegalArgumentException("Tipo de ação inválida: " + acao.tipoAcao());
        }
    
        PersonagemDTO ninja1Dto = personagemMapper.toDto(batalha.getNinja1());
        PersonagemDTO ninja2Dto = personagemMapper.toDto(batalha.getNinja2());
        
        ninja1Dto.setChakra(batalha.getChackraNinja1());
        ninja2Dto.setChakra(batalha.getChackraNinja2());
    
        return criarBatalhaResponse(batalha, mensagem);
    }
        
    private String processarJutsu(Batalha batalha, Personagem atacante, Personagem defensor, String nomeJutsu) {
        logPort.debug("Processando jutsu: {} usando {} contra {}", 
            atacante.getNome(), nomeJutsu, defensor.getNome());
    
        atacante.getJutsus().stream()
            .filter(j -> j.equals(nomeJutsu))
            .findFirst()
            .orElseThrow(() -> new JutsuNaoEncontradoException(nomeJutsu));
    
        if (!temChakraSuficiente(atacante)) {
            return String.format("%s não tem chakra suficiente para usar %s", 
                atacante.getNome(), nomeJutsu);
        }
    
        int novoChakra = atacante.getChakra() - 10;
        atacante.setChakra(novoChakra);
        
        if (atacante.getId().equals(batalha.getNinja1().getId())) {
            batalha.setChackraNinja1(novoChakra);
            batalha.getNinja1().setChakra(novoChakra);
        } else {
            batalha.setChackraNinja2(novoChakra);
            batalha.getNinja2().setChakra(novoChakra);
        }
    
        int danoBase = switch (atacante.getTipoNinja()) {
            case NINJUTSU -> 40;
            case GENJUTSU -> 35;
            case TAIJUTSU -> 45;
        };
    
        if (batalha.temAtaquePendente()) {
            AtaquePendente ataquePendente = batalha.getAtaquePendente();
            int dano = calcularDano(ataquePendente.danoBase());
            batalha.aplicarDano(atacante.getId(), dano);
            
            String mensagem = String.format("%s tentou usar %s mas foi atingido por %s e recebeu %d de dano!", 
                atacante.getNome(), nomeJutsu, ataquePendente.nomeJutsu(), dano);
            
            batalha.limparAtaquePendente();
            return mensagem;
        }
    
        batalha.registrarAtaquePendente(atacante.getId(), nomeJutsu, danoBase);
        logPort.debug("Ataque pendente registrado: {}", batalha.getAtaquePendente());
        return String.format("%s está preparando %s! %s pode tentar desviar!", 
            atacante.getNome(), nomeJutsu, defensor.getNome());
    }
    
    private String processarDesvio(Batalha batalha, Personagem atacante, Personagem defensor) {
        logPort.debug("Processando desvio para {} contra ataque de {}", 
        defensor.getNome(), atacante.getNome());
        if (!batalha.temAtaquePendente()) {
            logPort.debug("Tentativa de desvio sem ataque pendente para {}", defensor.getNome());
            return String.format("%s tentou desviar, mas não havia nenhum ataque pendente!", 
                defensor.getNome());
        }
    
        AtaquePendente ataque = batalha.getAtaquePendente();
        double chanceDesvio = calcularChanceDesvio(defensor);
        boolean conseguiuDesviar = random.nextDouble() <= chanceDesvio;
            
        if (conseguiuDesviar) {
            batalha.limparAtaquePendente();
            return String.format("%s conseguiu desviar do %s!", 
                defensor.getNome(), ataque.nomeJutsu());
        }
        
        int dano = calcularDano(ataque.danoBase());
        batalha.aplicarDano(defensor.getId(), dano);
        batalha.limparAtaquePendente();
        
        return String.format("%s não conseguiu desviar do %s e recebeu %d de dano!", 
            defensor.getNome(), ataque.nomeJutsu(), dano);
    }
    
    private double calcularChanceDesvio(Personagem ninja) {
        return switch (ninja.getTipoNinja()) {
            case TAIJUTSU -> 0.4;
            case NINJUTSU -> 0.3;
            case GENJUTSU -> 0.2;
        };
    }

    private int calcularDano(int danoBase) {
        return danoBase + random.nextInt(10);
    }

    private boolean temChakraSuficiente(Personagem ninja) {
        return ninja.getChakra() >= 10;
    }

    private String determinarVencedor(Batalha batalha) {
        return batalha.getVidaNinja1() <= 0 ? 
            batalha.getNinja2().getNome() : 
            batalha.getNinja1().getNome();
    }

    private BatalhaResponseDTO criarBatalhaResponse(Batalha batalha, String mensagem) {
        var ninja1Dto = personagemMapper.toBatalhaEstadoDto(batalha.getNinja1());
        var ninja2Dto = personagemMapper.toBatalhaEstadoDto(batalha.getNinja2());
        
        ninja1Dto.setPontosDeVida(batalha.getVidaNinja1());
        ninja2Dto.setPontosDeVida(batalha.getVidaNinja2());
    
        return new BatalhaResponseDTO(
            batalha.getId(),
            ninja1Dto,
            ninja2Dto,
            batalha.isFinalizada(),
            batalha.getTurnoAtual(),
            batalha.getNinjaAtual(),
            mensagem
        );
    }

    private String gerarMensagemDeAtaque(Personagem atacante, Personagem defensor, AtaquePendente ataque) {
        return String.format("%s está preparando %s! %s pode tentar desviar!", 
        atacante.getNome(), ataque.nomeJutsu(), defensor.getNome());
    }
    
    private String gerarMensagemDeRodada(Batalha batalha) {
        String ninjaAtualNome = batalha.getNinjaAtual().equals(batalha.getNinja1().getId()) 
                ? batalha.getNinja1().getNome() 
                : batalha.getNinja2().getNome();

        return String.format("Turno %d - Vez de %s",
                batalha.getTurnoAtual(), ninjaAtualNome);
    }
}
