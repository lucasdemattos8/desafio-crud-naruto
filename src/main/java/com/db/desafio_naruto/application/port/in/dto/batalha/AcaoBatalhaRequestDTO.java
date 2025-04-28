package com.db.desafio_naruto.application.port.in.dto.batalha;

import com.db.desafio_naruto.domain.model.enums.TipoAcao;

public record AcaoBatalhaRequestDTO(
    Long ninjaId,
    TipoAcao tipoAcao,
    String nomeJutsu
) {}
