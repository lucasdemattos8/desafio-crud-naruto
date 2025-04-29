package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.db.desafio_naruto.application.port.out.JutsuPort;
import com.db.desafio_naruto.domain.model.Jutsu;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.JutsuMapper;

@Component
public class JutsuPersistenceAdapter implements JutsuPort {
    private final JutsuJpaRepository repository;
    private final JutsuMapper mapper;

    public JutsuPersistenceAdapter(JutsuJpaRepository repository, JutsuMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Jutsu> buscarPorNome(String nome) {
        return repository.findByNome(nome)
                .map(mapper::toDomain);
    }

    @Override
    public Jutsu salvar(Jutsu jutsu) {
        var entity = mapper.toEntity(jutsu);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }
}
