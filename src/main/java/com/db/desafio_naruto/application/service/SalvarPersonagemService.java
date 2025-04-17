package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.SalvarPersonagemUseCase;
import com.db.desafio_naruto.application.port.out.SalvarPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;

@Service
public class SalvarPersonagemService implements SalvarPersonagemUseCase {

    private final SalvarPersonagemPort salvarPersonagemPort;

    public SalvarPersonagemService(SalvarPersonagemPort salvarPersonagemPort) {
        this.salvarPersonagemPort = salvarPersonagemPort;
    }

    @Override
    public Personagem salvar(Personagem personagem) {
        return salvarPersonagemPort.salvar(personagem);                                          
    }
    
}
                              