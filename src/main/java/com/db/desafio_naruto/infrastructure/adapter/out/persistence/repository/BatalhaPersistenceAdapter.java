package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.db.desafio_naruto.application.port.out.SalvarBatalhaPort;
import com.db.desafio_naruto.domain.model.Batalha;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.BatalhaMapper;

@Component
public class BatalhaPersistenceAdapter implements SalvarBatalhaPort {
    private final BatalhaJpaRepository batalhaRepository;
    private final BatalhaMapper batalhaMapper;

    public BatalhaPersistenceAdapter(
        BatalhaJpaRepository batalhaJpaRepository, BatalhaMapper batalhaMapper) {
            this.batalhaRepository = batalhaJpaRepository;
            this.batalhaMapper = batalhaMapper;
        }

    @Override
    public Batalha salvar(Batalha batalha) {
        var entity = batalhaMapper.toEntity(batalha);
        entity = batalhaRepository.save(entity);
        return batalhaMapper.toDomain(entity);
    }

    @Override
    public Optional<Batalha> buscarPorId(Long id) {
        return batalhaRepository.findById(id)
            .map(batalhaMapper::toDomain);
    }
}
