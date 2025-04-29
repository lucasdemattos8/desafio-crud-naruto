package com.db.desafio_naruto.application.service.exceptions;

public class TurnoInvalidoException extends RuntimeException {
    public TurnoInvalidoException() {
        super("Não é turno deste ninja jogar");
    }
}
