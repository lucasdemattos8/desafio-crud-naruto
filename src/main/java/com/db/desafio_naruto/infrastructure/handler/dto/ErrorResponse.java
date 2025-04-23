package com.db.desafio_naruto.infrastructure.handler.dto;

public record ErrorResponse(int status, String error, String message) {}
