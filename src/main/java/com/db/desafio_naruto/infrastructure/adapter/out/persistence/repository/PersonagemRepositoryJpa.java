package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.desafio_naruto.infrastructure.adapter.out.persistence.PersonagemEntity;

public interface PersonagemRepositoryJpa extends JpaRepository<PersonagemEntity, Long> {
    
}
