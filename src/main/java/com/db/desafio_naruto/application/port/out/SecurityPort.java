package com.db.desafio_naruto.application.port.out;

public interface SecurityPort {
    String gerarToken();
    boolean validarToken(String token);
}
