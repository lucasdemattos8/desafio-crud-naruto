package com.db.desafio_naruto.infrastructure.adapter.out.security;

import org.springframework.stereotype.Component;
import com.db.desafio_naruto.application.port.out.SecurityPort;
import com.db.desafio_naruto.infrastructure.security.JwtTokenService;

@Component
public class SecurityAdapter implements SecurityPort {
    
    private final JwtTokenService jwtTokenService;

    public SecurityAdapter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public String gerarToken() {
        return jwtTokenService.generateToken();
    }

    @Override
    public boolean validarToken(String token) {
        return jwtTokenService.validateToken(token);
    }
}