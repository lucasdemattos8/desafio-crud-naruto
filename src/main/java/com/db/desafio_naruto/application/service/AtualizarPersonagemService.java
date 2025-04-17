package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.AtualizarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.command.AtualizarPersonagemCommand;
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
    public Personagem atualizar(AtualizarPersonagemCommand command) {
        buscarPorIdPersonagemPort.buscarPorId(command.getId())
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));
        
        Personagem personagem = new Personagem();
        personagem.setId(command.getId());
        personagem.setNome(command.getNome());
        personagem.setIdade(command.getIdade());
        personagem.setAldeia(command.getAldeia());
        personagem.setJutsus(command.getJutsus());
        personagem.setChakra(command.getChakra());
        personagem.setTipoNinja(command.getTipoNinja());

        return atualizarPersonagemPort.atualizar(personagem);
    }
}