package com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.db.desafio_naruto.domain.model.Jutsu;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.JutsuEntity;

@Component
public class JutsuMapper {
    public Jutsu toDomain(JutsuEntity entity) {
        return new Jutsu(
            entity.getId(),
            entity.getNome(),
            entity.getCustoChakra()
        );
    }

    public JutsuEntity toEntity(Jutsu jutsu) {
        return new JutsuEntity(
            jutsu.getNome(),
            jutsu.getCustoChakra()
        );
    }
}
