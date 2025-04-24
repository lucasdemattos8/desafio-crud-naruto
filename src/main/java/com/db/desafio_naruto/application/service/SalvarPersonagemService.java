package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.SalvarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.command.CriarPersonagemCommand;
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
    public Personagem salvar(CriarPersonagemCommand personagem) {
        logPort.info("Iniciando salvamento do personagem: {}", personagem.getNome());
        try {
            personagem.validarCampos();
            Personagem saved = salvarPersonagemPort.salvar(toDomain(personagem));
            logPort.debug("Personagem salvo com sucesso: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            logPort.error("Erro ao salvar personagem: {}", personagem.getNome(), e);
            throw e;
        }                                         
    }

    private Personagem toDomain(CriarPersonagemCommand personagemCommand) {
        Personagem personagem = new Personagem(
            null,
            personagemCommand.getNome(),
            personagemCommand.getIdade(),
            personagemCommand.getAldeia(),
            personagemCommand.getJutsus(),
            personagemCommand.getChakra());
        personagem.setTipoNinja(personagemCommand.getTipoNinja());
        return personagem;
    }
    
}
                              