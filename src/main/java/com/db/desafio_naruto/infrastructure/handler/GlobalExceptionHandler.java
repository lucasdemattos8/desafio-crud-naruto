package com.db.desafio_naruto.infrastructure.handler;

import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.application.service.exceptions.BatalhaNaoEncontradaException;
import com.db.desafio_naruto.application.service.exceptions.JutsuNaoEncontradoException;
import com.db.desafio_naruto.application.service.exceptions.PersonagemNaoEncontradoException;
import com.db.desafio_naruto.application.service.exceptions.TurnoInvalidoException;
import com.db.desafio_naruto.infrastructure.handler.dto.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LogPort logPort;

    public GlobalExceptionHandler(LogPort logPort) {
        this.logPort = logPort;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Acesso negado",
                ex.getMessage()
            ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Erro de autenticação",
                ex.getMessage()
            ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGenericEntityNotFoundException(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Entidade não encontrada",
                ex.getMessage()
            ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                ex.getMessage()
            ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        logPort.error("Erro de validação ao processar requisição: {}", ex.getMessage());
        
        String mensagem = ex.getMessage();
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            if (cause.getTargetType() != null && cause.getTargetType().isEnum()) {
                mensagem = String.format(
                    "Valor inválido '%s'. Valores aceitos: %s", 
                    cause.getValue(), 
                    Arrays.toString(cause.getTargetType().getEnumConstants())
                );
                logPort.debug("Erro de enum inválido: {}", mensagem);
            }
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                mensagem
            ));
    }

    @ExceptionHandler(BatalhaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleBatalhaNaoEncontrada(BatalhaNaoEncontradaException ex) {
        logPort.error("Batalha não encontrada: {}", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Batalha não encontrada",
                ex.getMessage()
            ));
    }

    @ExceptionHandler(TurnoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleTurnoInvalido(TurnoInvalidoException ex) {
        logPort.error("Tentativa de jogar em turno inválido: {}", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Turno inválido",
                ex.getMessage()
            ));
    }

    @ExceptionHandler(JutsuNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleJutsuNaoEncontrado(JutsuNaoEncontradoException ex) {
        logPort.error("Jutsu não encontrado: {}", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Jutsu não encontrado",
                ex.getMessage()
            ));
    }

    @ExceptionHandler(PersonagemNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handlePersonagemNaoEncontrado(PersonagemNaoEncontradoException ex) {
        logPort.error("Personagem não encontrado: {}", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Personagem não encontrado",
                ex.getMessage()
            ));
    }
}
