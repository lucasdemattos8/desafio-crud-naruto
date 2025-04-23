package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.BuscarPorIdPersonagemUseCase;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BuscarPorIdPersonagemService implements BuscarPorIdPersonagemUseCase {

    private final BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;

    public BuscarPorIdPersonagemService(BuscarPorIdPersonagemPort buscarPorIdPersonagemPort) {
        this.buscarPorIdPersonagemPort = buscarPorIdPersonagemPort;
    }

    @Override
    public Personagem buscarPorID(Long id) {
        Personagem personagem = buscarPorIdPersonagemPort.buscarPorId(id)
        .orElseThrow(() -> new EntityNotFoundException("Personagem com ID " + id + " não encontrado!"));

        return personagem;
    }
    
}
