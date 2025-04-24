package com.db.desafio_naruto.application.port.in;

import com.db.desafio_naruto.application.port.in.command.CriarPersonagemCommand;
import com.db.desafio_naruto.domain.model.Personagem;

public interface SalvarPersonagemUseCase {
    Personagem salvar(CriarPersonagemCommand personagem);
}
