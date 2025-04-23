package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.AutenticacaoUseCase;
import com.db.desafio_naruto.application.port.out.SecurityPort;

@Service
public class AuthService implements AutenticacaoUseCase {
    
    private final SecurityPort securityPort;

    public AuthService(SecurityPort securityPort) {
        this.securityPort = securityPort;
    }

    @Override
    public String autenticar() {
        return securityPort.gerarToken();
    }
}