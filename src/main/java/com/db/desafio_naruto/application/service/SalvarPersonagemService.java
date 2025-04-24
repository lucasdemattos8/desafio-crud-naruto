package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.SalvarPersonagemUseCase;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.application.port.out.SalvarPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;

@Service
public class SalvarPersonagemService implements SalvarPersonagemUseCase {

    private final SalvarPersonagemPort salvarPersonagemPort;
    private final LogPort logPort;

    public SalvarPersonagemService(SalvarPersonagemPort salvarPersonagemPort, LogPort logPort) {
        this.salvarPersonagemPort = salvarPersonagemPort;
        this.logPort = logPort;
    }

    @Override
    public Personagem salvar(Personagem personagem) {
        logPort.info("Iniciando salvamento do personagem: {}", personagem.getNome());
        try {
            Personagem saved = salvarPersonagemPort.salvar(personagem);
            logPort.debug("Personagem salvo com sucesso: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            logPort.error("Erro ao salvar personagem: {}", personagem.getNome(), e);
            throw e;
        }                                         
    }
    
}
                              