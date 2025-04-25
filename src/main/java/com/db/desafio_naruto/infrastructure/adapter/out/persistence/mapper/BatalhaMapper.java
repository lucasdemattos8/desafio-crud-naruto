package com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.db.desafio_naruto.domain.model.Batalha;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.BatalhaEntity;

@Component
public class BatalhaMapper {
    private final PersonagemMapper personagemMapper;

    public BatalhaMapper(PersonagemMapper personagemMapper) {
        this.personagemMapper = personagemMapper;
    }

    public BatalhaEntity toEntity(Batalha batalha) {
        BatalhaEntity entity = new BatalhaEntity(
            batalha.getId(),
            personagemMapper.toEntity(batalha.getNinja1()),
            personagemMapper.toEntity(batalha.getNinja2()),
            batalha.isFinalizada(),
            batalha.getTurnoAtual(),
            batalha.getNinjaAtual(),
            batalha.getVidaNinja1(), 
            batalha.getVidaNinja2() 
        );

        if (batalha.getAtaquePendente() != null) {
            entity.setAtaqueNinjaId(batalha.getAtaquePendente().ninjaAtacanteId());
            entity.setAtaqueJutsu(batalha.getAtaquePendente().nomeJutsu());
            entity.setAtaqueDanoBase(batalha.getAtaquePendente().danoBase());
        }
        
        return entity;
    }

    public Batalha toDomain(BatalhaEntity entity) {
        Personagem ninja1 = personagemMapper.toDomain(entity.getNinja1());
        Personagem ninja2 = personagemMapper.toDomain(entity.getNinja2());

        Batalha batalha = new Batalha(
            entity.getId(),
            ninja1,
            ninja2,
            entity.isFinalizada(),
            entity.getTurnoAtual(),
            entity.getNinjaAtual()
        );

        if (entity.getAtaqueNinjaId() != null) {
            batalha.registrarAtaquePendente(
                entity.getAtaqueNinjaId(),
                entity.getAtaqueJutsu(),
                entity.getAtaqueDanoBase()
            );
        }

        return batalha;
    }
}
