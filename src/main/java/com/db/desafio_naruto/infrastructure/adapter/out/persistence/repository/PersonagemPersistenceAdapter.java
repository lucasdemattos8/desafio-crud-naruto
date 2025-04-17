package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.db.desafio_naruto.application.port.out.AtualizarPersonagemPort;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.BuscarTodosPersonagensPort;
import com.db.desafio_naruto.application.port.out.DeletarPersonagemPort;
import com.db.desafio_naruto.application.port.out.SalvarPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.PersonagemEntity;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.PersonagemPersistenceMapper;

@Component
public class PersonagemPersistenceAdapter implements 
        SalvarPersonagemPort,
        BuscarPorIdPersonagemPort,
        DeletarPersonagemPort,
        AtualizarPersonagemPort,
        BuscarTodosPersonagensPort {

    private final PersonagemJpaRepository personagemRepository;
    private final PersonagemPersistenceMapper mapper;

    public PersonagemPersistenceAdapter(
        PersonagemJpaRepository personagemRepository, PersonagemPersistenceMapper mapper) {
        this.personagemRepository = personagemRepository;
        this.mapper = mapper;
    }

    @Override
    public Personagem salvar(Personagem personagem) {
        PersonagemEntity entity = mapper.toEntity(personagem);
        entity = personagemRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Page<Personagem> buscarTodos(Pageable pageable) {
        return personagemRepository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Personagem> buscarPorId(Long id) {
        PersonagemEntity entity = personagemRepository.findById(id).get();
        return Optional.of(mapper.toDomain(entity));
    }

    @Override
    public Personagem atualizar(Personagem personagem) {
        PersonagemEntity entity = mapper.toEntity(personagem);
        entity = personagemRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public void deletar(Long id) {
        personagemRepository.deleteById(id);
    }
}
