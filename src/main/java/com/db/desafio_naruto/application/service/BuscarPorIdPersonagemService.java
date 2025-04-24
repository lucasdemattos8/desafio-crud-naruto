package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.BuscarPorIdPersonagemUseCase;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.domain.model.Personagem;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BuscarPorIdPersonagemService implements BuscarPorIdPersonagemUseCase {

    private final BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;
    private final LogPort logPort;

    public BuscarPorIdPersonagemService(
            BuscarPorIdPersonagemPort buscarPorIdPersonagemPort,
            LogPort logPort) {
        this.buscarPorIdPersonagemPort = buscarPorIdPersonagemPort;
        this.logPort = logPort;
    }

    @Override
    public Personagem buscarPorID(Long id) {
        logPort.info("Iniciando busca de personagem por ID: {}", id);
        
        try {
            Personagem personagem = buscarPorIdPersonagemPort.buscarPorId(id)
                .orElseThrow(() -> {
                    logPort.error("Personagem não encontrado com ID: {}", id);
                    return new EntityNotFoundException("Personagem com ID " + id + " não encontrado!");
                });

            logPort.debug("Personagem encontrado: {} (ID: {})", personagem.getNome(), id);
            return personagem;
            
        } catch (Exception e) {
            logPort.error("Erro ao buscar personagem com ID: {}", id, e);
            throw e;
        }
    }
}