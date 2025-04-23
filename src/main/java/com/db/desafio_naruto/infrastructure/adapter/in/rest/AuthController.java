package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import com.db.desafio_naruto.application.port.in.AutenticacaoUseCase;
import com.db.desafio_naruto.infrastructure.handler.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints para gerenciamento de autenticação")
public class AuthController {

    private final AutenticacaoUseCase autenticacaoUseCase;

    public AuthController(AutenticacaoUseCase autenticacaoUseCase) {
        this.autenticacaoUseCase = autenticacaoUseCase;
    }

    @Operation(
        summary = "Gerar token de autenticação",
        description = "Gera um token JWT para autenticar requisições subsequentes na API"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token gerado com sucesso",
            content = @Content(schema = @Schema(implementation = TokenResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno ao gerar token",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<TokenResponse> getToken() {
        String token = autenticacaoUseCase.autenticar();
        return ResponseEntity.ok(new TokenResponse(token));
    }
}

@Schema(description = "Resposta contendo o token de autenticação")
record TokenResponse(@Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String token) {}