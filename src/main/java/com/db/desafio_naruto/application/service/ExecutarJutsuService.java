package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;
import com.db.desafio_naruto.application.port.in.ExecutarJutsuUseCase;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.domain.model.*;
import com.db.desafio_naruto.domain.model.interfaces.Ninja;

@Service
public class ExecutarJutsuService implements ExecutarJutsuUseCase {
    
    private final BuscarPorIdPersonagemPort personagemRepositoryPort;
    private final LogPort logPort;

    public ExecutarJutsuService(BuscarPorIdPersonagemPort personagemRepositoryPort, LogPort logPort) {
        this.personagemRepositoryPort = personagemRepositoryPort;
        this.logPort = logPort;
    }

    @Override
    public String executar(Long id, boolean isDesviar) {
        logPort.info("Iniciando execução de jutsu para personagem ID: {} com desvio: {}", id, isDesviar);
        
        try {
            Personagem personagemBase = personagemRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> {
                    logPort.error("Personagem não encontrado com ID: {}", id);
                    return new RuntimeException("Personagem não encontrado");
                });
            
            logPort.debug("Personagem encontrado: {} do tipo {}", personagemBase.getNome(), personagemBase.getTipoNinja());
            
            Ninja ninja = switch (personagemBase.getTipoNinja()) {
                case NINJUTSU -> {
                    logPort.debug("Criando ninja de Ninjutsu para: {}", personagemBase.getNome());
                    NinjaDeNinjutsu ninjaDeNinjutsu = new NinjaDeNinjutsu();
                    copyProperties(personagemBase, ninjaDeNinjutsu);
                    yield ninjaDeNinjutsu;
                }
                case GENJUTSU -> {
                    logPort.debug("Criando ninja de Genjutsu para: {}", personagemBase.getNome());
                    NinjaDeGenjutsu ninjaDeGenjutsu = new NinjaDeGenjutsu();
                    copyProperties(personagemBase, ninjaDeGenjutsu);
                    yield ninjaDeGenjutsu;
                }
                case TAIJUTSU -> {
                    logPort.debug("Criando ninja de Taijutsu para: {}", personagemBase.getNome());
                    NinjaDeTaijutsu ninjaDeTaijutsu = new NinjaDeTaijutsu();
                    copyProperties(personagemBase, ninjaDeTaijutsu);
                    yield ninjaDeTaijutsu;
                }
                default -> {
                    logPort.error("Tipo de ninja não suportado: {} para personagem: {}", 
                        personagemBase.getTipoNinja(), personagemBase.getNome());
                    throw new IllegalArgumentException("Tipo de ninja não suportado: " + personagemBase.getTipoNinja());
                }
            };

            String resultado = isDesviar ? ninja.desviar() : ninja.usarJutsu();
            logPort.info("Jutsu executado com sucesso para {}: {}", personagemBase.getNome(), resultado);
            return resultado;
            
        } catch (Exception e) {
            logPort.error("Erro ao executar jutsu para personagem ID: {}", id, e);
            throw e;
        }
    }

    private void copyProperties(Personagem source, Personagem target) {
        logPort.debug("Copiando propriedades do personagem ID: {}", source.getId());
        target.setId(source.getId());
        target.setNome(source.getNome());
        target.setIdade(source.getIdade());
        target.setAldeia(source.getAldeia());
        target.setJutsus(source.getJutsus());
        target.setChakra(source.getChakra());
        target.setTipoNinja(source.getTipoNinja());
    }
}