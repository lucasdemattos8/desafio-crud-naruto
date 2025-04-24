package com.db.desafio_naruto.infrastructure.adapter.out.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.db.desafio_naruto.application.port.out.LogPort;

@Component
public class Slf4jLogAdapter implements LogPort {
    private final Logger logger;

    public Slf4jLogAdapter() {
        this.logger = LoggerFactory.getLogger(Slf4jLogAdapter.class);
    }

    @Override
    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    @Override
    public void error(String message, Object... args) {
        logger.error(message, args);
    }

    @Override
    public void debug(String message, Object... args) {
        logger.debug(message, args);
    }
}