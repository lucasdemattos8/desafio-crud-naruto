package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.desafio_naruto.infrastructure.adapter.out.persistence.JutsuEntity;

public interface JutsuJpaRepository extends JpaRepository<JutsuEntity, Long> {
    Optional<JutsuEntity> findByNome(String nome);
}
