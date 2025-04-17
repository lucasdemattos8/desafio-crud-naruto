package com.db.desafio_naruto.application.port.in;

import com.db.desafio_naruto.application.port.in.command.AtualizarPersonagemCommand;
import com.db.desafio_naruto.domain.model.Personagem;

public interface AtualizarPersonagemUseCase {
    Personagem atualizar(AtualizarPersonagemCommand personagem);
}
