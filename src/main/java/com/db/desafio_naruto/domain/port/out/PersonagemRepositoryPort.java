package com.db.desafio_naruto.domain.port.out;

import com.db.desafio_naruto.domain.model.Personagem;

public interface PersonagemRepositoryPort {
    Personagem save(Personagem person);
    Personagem findById(Long id);
    void delete(Long id);
}
