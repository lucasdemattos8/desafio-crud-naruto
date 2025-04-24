package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.AtualizarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.command.AtualizarPersonagemCommand;
import com.db.desafio_naruto.application.port.out.AtualizarPersonagemPort;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.domain.model.Personagem;

@Service
public class AtualizarPersonagemService implements AtualizarPersonagemUseCase {
    
    private final AtualizarPersonagemPort atualizarPersonagemPort;
    private final BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;
    private final LogPort logPort;

    public AtualizarPersonagemService(
            AtualizarPersonagemPort atualizarPersonagemPort,
            BuscarPorIdPersonagemPort buscarPorIdPersonagemPort,
            LogPort logPort) {
        this.atualizarPersonagemPort = atualizarPersonagemPort;
        this.buscarPorIdPersonagemPort = buscarPorIdPersonagemPort;
        this.logPort = logPort;
    }

    @Override
    public Personagem atualizar(AtualizarPersonagemCommand command) {
        logPort.info("Iniciando atualização do personagem ID: {}", command.getId());
        
        try {
            buscarPorIdPersonagemPort.buscarPorId(command.getId())
                .orElseThrow(() -> {
                    logPort.error("Tentativa de atualizar personagem inexistente. ID: {}", command.getId());
                    return new RuntimeException("Personagem não encontrado");
                });
            
            logPort.debug("Personagem encontrado. Atualizando dados para ID: {}", command.getId());
            
            Personagem personagem = new Personagem();
            personagem.setId(command.getId());
            personagem.setNome(command.getNome());
            personagem.setIdade(command.getIdade());
            personagem.setAldeia(command.getAldeia());
            personagem.setJutsus(command.getJutsus());
            personagem.setChakra(command.getChakra());
            personagem.setTipoNinja(command.getTipoNinja());

            Personagem atualizado = atualizarPersonagemPort.atualizar(personagem);
            logPort.info("Personagem atualizado com sucesso: {} (ID: {})", atualizado.getNome(), atualizado.getId());
            
            return atualizado;
            
        } catch (Exception e) {
            logPort.error("Erro ao atualizar personagem ID: {}", command.getId(), e);
            throw e;
        }
    }
}