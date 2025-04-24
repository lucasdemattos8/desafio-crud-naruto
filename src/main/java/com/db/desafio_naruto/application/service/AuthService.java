package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.AutenticacaoUseCase;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.application.port.out.SecurityPort;

@Service
public class AuthService implements AutenticacaoUseCase {
    
    private final SecurityPort securityPort;
    private final LogPort logPort;

    public AuthService(SecurityPort securityPort, LogPort logPort) {
        this.securityPort = securityPort;
        this.logPort = logPort;
    }

    @Override
    public String autenticar() {
        logPort.info("Iniciando processo de autenticação");
        try {
            String token = securityPort.gerarToken();
            logPort.debug("Token gerado com sucesso");
            return token;
        } catch (Exception e) {
            logPort.error("Erro ao gerar token de autenticação", e);
            throw e;
        }
    }
}