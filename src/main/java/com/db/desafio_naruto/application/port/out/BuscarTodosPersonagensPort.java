package com.db.desafio_naruto.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.db.desafio_naruto.domain.model.Personagem;

public interface BuscarTodosPersonagensPort {
    Page<Personagem> buscarTodos(Pageable pageable);
}