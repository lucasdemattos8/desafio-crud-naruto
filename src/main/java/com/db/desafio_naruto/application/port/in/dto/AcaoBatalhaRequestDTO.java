package com.db.desafio_naruto.application.port.in.dto;

import com.db.desafio_naruto.domain.model.enums.TipoAcao;

public record AcaoBatalhaRequestDTO(
    Long ninjaId,
    TipoAcao acao,
    String nomeJutsu
) {}
