package com.db.desafio_naruto.application.port.in;

import com.db.desafio_naruto.domain.model.Personagem;

public interface BuscarPorIdPersonagemUseCase {
    Personagem buscarPorID(Long id);
}
