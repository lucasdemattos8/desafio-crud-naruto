package com.db.desafio_naruto.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.BuscarTodosPersonagensUseCase;
import com.db.desafio_naruto.application.port.out.BuscarTodosPersonagensPort;
import com.db.desafio_naruto.domain.model.Personagem;

@Service
public class BuscarTodosPersonagensService implements BuscarTodosPersonagensUseCase {

    private final BuscarTodosPersonagensPort buscarTodosPersonagensPort;

    public BuscarTodosPersonagensService(BuscarTodosPersonagensPort buscarTodosPersonagensPort) {
        this.buscarTodosPersonagensPort = buscarTodosPersonagensPort;
    }

    @Override
    public Page<Personagem> buscarTodos(Pageable pageable) {
        return buscarTodosPersonagensPort.buscarTodos(pageable);
    }
}