package com.db.desafio_naruto.application.port.out;

import java.util.Optional;

import com.db.desafio_naruto.domain.model.Personagem;

public interface BuscarPorIdPersonagemPort {
    Optional<Personagem> buscarPorId(Long id);
}
