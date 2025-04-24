package com.db.desafio_naruto.application.port.out;

public interface LogPort {
    void info(String message, Object... args);
    void error(String message, Object... args);
    void debug(String message, Object... args);
}