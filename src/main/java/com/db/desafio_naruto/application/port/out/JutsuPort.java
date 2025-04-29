package com.db.desafio_naruto.application.port.out;

import java.util.Optional;

import com.db.desafio_naruto.domain.model.Jutsu;

public interface JutsuPort {
    Optional<Jutsu> buscarPorNome(String nome);
    Jutsu salvar(Jutsu jutsu);
}
