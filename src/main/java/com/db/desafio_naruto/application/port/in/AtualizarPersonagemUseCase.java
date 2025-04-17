package com.db.desafio_naruto.application.port.in;

import com.db.desafio_naruto.domain.model.Personagem;

public interface AtualizarPersonagemUseCase {
    Personagem atualizar(Long id, Personagem personagem);
}
