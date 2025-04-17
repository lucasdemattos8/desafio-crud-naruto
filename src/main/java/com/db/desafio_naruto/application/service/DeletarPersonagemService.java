package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.DeletarPersonagemUseCase;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.DeletarPersonagemPort;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeletarPersonagemService implements DeletarPersonagemUseCase {

    private final DeletarPersonagemPort deletarPersonagemPort;
    private final BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;

    public DeletarPersonagemService(DeletarPersonagemPort deletarPersonagemPort, 
                                  BuscarPorIdPersonagemPort buscarPorIdPersonagemPort) {
        this.deletarPersonagemPort = deletarPersonagemPort;
        this.buscarPorIdPersonagemPort = buscarPorIdPersonagemPort;
    }

    @Override
    public void deletar(Long id) {
        buscarPorIdPersonagemPort.buscarPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Personagem com ID " + id + " não encontrado!"));
        
            deletarPersonagemPort.deletar(id);
    }
    
}
