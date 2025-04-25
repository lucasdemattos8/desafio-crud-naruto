package com.db.desafio_naruto.application.port.in.dto;

public record BatalhaResponseDTO(
    Long id,
    PersonagemDTO ninja1,
    PersonagemDTO ninja2,
    boolean finalizada,
    int turnoAtual,
    Long ninjaAtual,
    String mensagem
) {}