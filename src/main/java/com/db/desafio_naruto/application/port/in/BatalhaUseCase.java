package com.db.desafio_naruto.application.port.in;

import com.db.desafio_naruto.application.port.in.dto.batalha.AcaoBatalhaRequestDTO;
import com.db.desafio_naruto.application.port.in.dto.batalha.BatalhaResponseDTO;
import com.db.desafio_naruto.application.port.in.dto.batalha.IniciarBatalhaRequestDTO;

public interface BatalhaUseCase {
    BatalhaResponseDTO iniciarBatalha(IniciarBatalhaRequestDTO request);
    BatalhaResponseDTO executarAcao(Long batalhaId, AcaoBatalhaRequestDTO request);
    BatalhaResponseDTO consultarBatalha(Long batalhaId);
}
