package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.AtualizarPersonagemUseCase;
import com.db.desafio_naruto.application.port.out.AtualizarPersonagemPort;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;

@Service
public class AtualizarPersonagemService implements AtualizarPersonagemUseCase {
    
    private final AtualizarPersonagemPort atualizarPersonagemPort;
    private final BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;

    public AtualizarPersonagemService(AtualizarPersonagemPort atualizarPersonagemPort,
                                    BuscarPorIdPersonagemPort buscarPorIdPersonagemPort) {
        this.atualizarPersonagemPort = atualizarPersonagemPort;
        this.buscarPorIdPersonagemPort = buscarPorIdPersonagemPort;
    }

    @Override
    public Personagem atualizar(Long id, Personagem personagem) {
        buscarPorIdPersonagemPort.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));
        
        personagem.setId(id);
        return atualizarPersonagemPort.atualizar(personagem);
    }
}