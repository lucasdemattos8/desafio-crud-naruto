package com.db.desafio_naruto.application.port.out;

import java.util.List;
import java.util.Optional;

import com.db.desafio_naruto.domain.model.Personagem;

public interface PersonagemRepository {
    Personagem salvar(Personagem personagem);
    Optional<Personagem> buscarPorId(Long id);
    void deletar(Long id);
    List<Personagem> buscarTodos();
}