package com.db.desafio_naruto.application.port.in.dto.batalha;

import com.db.desafio_naruto.application.port.in.dto.personagem.PersonagemBatalhaDTO;

public record BatalhaResponseDTO(
    Long id,
    PersonagemBatalhaDTO ninjaDesafiante,
    PersonagemBatalhaDTO ninjaDesafiado,
    boolean finalizada,
    int turnoAtual,
    Long ninjaAtual,
    String mensagem
) {}