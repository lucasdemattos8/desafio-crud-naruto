package com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.db.desafio_naruto.application.port.in.dto.PersonagemDTO;
import com.db.desafio_naruto.domain.model.NinjaDeGenjutsu;
import com.db.desafio_naruto.domain.model.NinjaDeNinjutsu;
import com.db.desafio_naruto.domain.model.NinjaDeTaijutsu;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.PersonagemEntity;

@Component
public class PersonagemPersistenceMapper {

    public PersonagemEntity toEntity(Personagem personagem) {
        PersonagemEntity entity = new PersonagemEntity();
        entity.setId(personagem.getId());
        entity.setNome(personagem.getNome());
        entity.setIdade(personagem.getIdade());
        entity.setAldeia(personagem.getAldeia());
        entity.setJutsus(personagem.getJutsus());
        entity.setChakra(personagem.getChakra());
        entity.setTipoNinja(personagem.getTipoNinja());
        return entity;
    }

    public Personagem toDomain(PersonagemEntity entity) {
        Personagem personagem;

        if (entity.getTipoNinja() == null) {
            throw new IllegalArgumentException("Tipo de Ninja não pode ser nulo");
        }
        
        switch (entity.getTipoNinja()) {
            case NINJUTSU -> personagem = new NinjaDeNinjutsu();
            case GENJUTSU -> personagem = new NinjaDeGenjutsu();
            case TAIJUTSU -> personagem = new NinjaDeTaijutsu();
            default -> throw new IllegalArgumentException("Tipo de ninja não suportado: " + entity.getTipoNinja());
        }

        personagem.setId(entity.getId());
        personagem.setNome(entity.getNome());
        personagem.setIdade(entity.getIdade());
        personagem.setAldeia(entity.getAldeia());
        personagem.setJutsus(entity.getJutsus());
        personagem.setChakra(entity.getChakra());
        personagem.setTipoNinja(entity.getTipoNinja());

        return personagem;
    }

    public PersonagemDTO toDto(Personagem personagem) {
        return new PersonagemDTO(
            personagem.getId(),
            personagem.getNome(),
            personagem.getIdade(),
            personagem.getAldeia(),
            personagem.getJutsus(),
            personagem.getChakra(),
            personagem.getTipoNinja()
        );
    }

    public List<PersonagemDTO> toDto(List<Personagem> personagens) {
        return personagens.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Page<PersonagemDTO> toDto(Page<Personagem> page) {
        return page.map(this::toDto);
    }
}