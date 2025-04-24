package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.DeletarPersonagemUseCase;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.DeletarPersonagemPort;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.domain.model.Personagem;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeletarPersonagemService implements DeletarPersonagemUseCase {

    private final DeletarPersonagemPort deletarPersonagemPort;
    private final BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;
    private final LogPort logPort;

    public DeletarPersonagemService(
            DeletarPersonagemPort deletarPersonagemPort, 
            BuscarPorIdPersonagemPort buscarPorIdPersonagemPort,
            LogPort logPort) {
        this.deletarPersonagemPort = deletarPersonagemPort;
        this.buscarPorIdPersonagemPort = buscarPorIdPersonagemPort;
        this.logPort = logPort;
    }

    @Override
    public void deletar(Long id) {
        logPort.info("Iniciando processo de deleção do personagem ID: {}", id);
        
        try {
            Personagem personagem = buscarPorIdPersonagemPort.buscarPorId(id)
                .orElseThrow(() -> {
                    logPort.error("Tentativa de deletar personagem inexistente. ID: {}", id);
                    return new EntityNotFoundException("Personagem com ID " + id + " não encontrado!");
                });
            
            logPort.debug("Personagem encontrado para deleção: {} (ID: {})", personagem.getNome(), id);
            
            deletarPersonagemPort.deletar(id);
            logPort.info("Personagem deletado com sucesso: {} (ID: {})", personagem.getNome(), id);
            
        } catch (Exception e) {
            logPort.error("Erro ao deletar personagem ID: {}", id, e);
            throw e;
        }
    }
}