package com.db.desafio_naruto.application.port.out;

import java.util.Optional;

import com.db.desafio_naruto.domain.model.Batalha;

public interface SalvarBatalhaPort {
    Batalha salvar(Batalha batalha);
    Optional<Batalha> buscarPorId(Long id);
}
