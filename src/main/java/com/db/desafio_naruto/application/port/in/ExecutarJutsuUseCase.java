package com.db.desafio_naruto.application.port.in;

public interface ExecutarJutsuUseCase {
    String executarJutsu(Long id, boolean isDesviar);
}