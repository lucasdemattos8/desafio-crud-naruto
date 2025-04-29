package com.db.desafio_naruto.application.service.exceptions;

public class JutsuNaoEncontradoException extends RuntimeException {
    public JutsuNaoEncontradoException(String nomeJutsu) {
        super("Jutsu não encontrado: " + nomeJutsu);
    }
}
