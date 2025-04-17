package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.db.desafio_naruto.application.port.out.PersonagemRepository;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.PersonagemEntity;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.PersonagemPersistenceMapper;

@Component
public class PersonagemRepositoryAdapter implements PersonagemRepository {

    private final PersonagemRepositoryJpa personagemRepository;
    private final PersonagemPersistenceMapper mapper;

    public PersonagemRepositoryAdapter(
        PersonagemRepositoryJpa personagemRepository, PersonagemPersistenceMapper mapper) {
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
    public void deletar(Long id) {
        personagemRepository.deleteById(id);
    }

    @Override
    public Optional<Personagem> buscarPorId(Long id) {
        PersonagemEntity entity = personagemRepository.findById(id).get();
        return Optional.of(mapper.toDomain(entity));
    }

    @Override
    public List<Personagem> buscarTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodos'");
    }
}
