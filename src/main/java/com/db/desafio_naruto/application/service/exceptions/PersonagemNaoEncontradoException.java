package com.db.desafio_naruto.application.service.exceptions;

public class PersonagemNaoEncontradoException extends RuntimeException {
    public PersonagemNaoEncontradoException(Long id) {
        super("Personagem não encontrado com id: " + id);
    }
}
