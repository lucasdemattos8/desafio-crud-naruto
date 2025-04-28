package com.db.desafio_naruto.application.port.in.dto.batalha;

import com.db.desafio_naruto.application.port.in.dto.personagem.PersonagemDTO;

public record BatalhaResponseDTO(
    Long id,
    PersonagemDTO ninja1,
    PersonagemDTO ninja2,
    boolean finalizada,
    int turnoAtual,
    Long ninjaAtual,
    String mensagem
) {}