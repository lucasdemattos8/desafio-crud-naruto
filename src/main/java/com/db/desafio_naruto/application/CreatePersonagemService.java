package com.db.desafio_naruto.application;

import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.CreatePersonagemUseCase;
import com.db.desafio_naruto.application.port.out.PersonagemRepository;
import com.db.desafio_naruto.domain.model.Personagem;

@Service
public class CreatePersonagemService implements CreatePersonagemUseCase {

    private final PersonagemRepository personagemRepository;

    public CreatePersonagemService(PersonagemRepository personagemRepository) {
        this.personagemRepository = personagemRepository;
    }

    @Override
    public Personagem criar(Personagem personagem) {
        return personagemRepository.salvar(personagem);                                          
    }
    
}
                              