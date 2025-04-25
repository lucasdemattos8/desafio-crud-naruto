package com.db.desafio_naruto.application.port.in;

import com.db.desafio_naruto.application.port.in.dto.AcaoBatalhaRequestDTO;
import com.db.desafio_naruto.application.port.in.dto.BatalhaResponseDTO;
import com.db.desafio_naruto.application.port.in.dto.IniciarBatalhaRequestDTO;

public interface BatalhaUseCase {
    BatalhaResponseDTO iniciarBatalha(IniciarBatalhaRequestDTO request);
    BatalhaResponseDTO executarAcao(Long batalhaId, AcaoBatalhaRequestDTO request);
    BatalhaResponseDTO consultarBatalha(Long batalhaId);
}
