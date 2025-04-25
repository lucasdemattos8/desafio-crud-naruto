package com.db.desafio_naruto.application.service.exceptions;

public class BatalhaNaoEncontradaException extends RuntimeException {
    public BatalhaNaoEncontradaException(Long id) {
        super("Batalha não encontrada com id: " + id);
    }
}
