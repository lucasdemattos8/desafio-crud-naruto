package com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.db.desafio_naruto.domain.model.Batalha;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.BatalhaEntity;

@Component
public class BatalhaMapper {
    private final PersonagemMapper personagemMapper;

    public BatalhaMapper(PersonagemMapper personagemMapper) {
        this.personagemMapper = personagemMapper;
    }

    public BatalhaEntity toEntity(Batalha batalha) {
        return new BatalhaEntity(
            batalha.getId(),
            personagemMapper.toEntity(batalha.getNinja1()),
            personagemMapper.toEntity(batalha.getNinja2()),
            batalha.isFinalizada(),
            batalha.getTurnoAtual(),
            batalha.getNinjaAtual(),
            batalha.getVidaNinja1(), 
            batalha.getVidaNinja2() 
        );
    }

    public Batalha toDomain(BatalhaEntity entity) {
        return new Batalha(
            entity.getId(),
            personagemMapper.toDomain(entity.getNinja1()),
            personagemMapper.toDomain(entity.getNinja2()),
            entity.isFinalizada(),
            entity.getTurnoAtual(),
            entity.getNinjaAtual(),
            entity.getVidaNinja1(),  
            entity.getVidaNinja2()  
        );
    }
}
