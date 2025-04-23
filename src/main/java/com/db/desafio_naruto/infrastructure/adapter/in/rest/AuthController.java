package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import com.db.desafio_naruto.application.port.in.AutenticacaoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AutenticacaoUseCase autenticacaoUseCase;

    public AuthController(AutenticacaoUseCase autenticacaoUseCase) {
        this.autenticacaoUseCase = autenticacaoUseCase;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> getToken() {
        String token = autenticacaoUseCase.autenticar();
        return ResponseEntity.ok(new TokenResponse(token));
    }
}

record TokenResponse(String token) {}
