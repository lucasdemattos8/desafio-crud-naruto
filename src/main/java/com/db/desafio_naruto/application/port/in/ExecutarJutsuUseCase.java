package com.db.desafio_naruto.application.port.in;

public interface ExecutarJutsuUseCase {
    String executar(Long id, boolean isDesviar);
}