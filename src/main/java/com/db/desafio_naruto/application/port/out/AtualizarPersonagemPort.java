package com.db.desafio_naruto.application.port.out;

import com.db.desafio_naruto.domain.model.Personagem;

public interface AtualizarPersonagemPort {
    Personagem atualizar(Personagem personagem);
}
