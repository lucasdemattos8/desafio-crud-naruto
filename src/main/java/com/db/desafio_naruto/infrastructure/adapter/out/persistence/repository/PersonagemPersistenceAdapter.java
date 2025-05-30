package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.db.desafio_naruto.application.port.out.AtualizarPersonagemPort;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.BuscarTodosPersonagensPort;
import com.db.desafio_naruto.application.port.out.DeletarPersonagemPort;
import com.db.desafio_naruto.application.port.out.SalvarPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.JutsuEntity;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.PersonagemEntity;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.PersonagemMapper;

@Component
public class PersonagemPersistenceAdapter implements 
        SalvarPersonagemPort,
        BuscarPorIdPersonagemPort,
        DeletarPersonagemPort,
        AtualizarPersonagemPort,
        BuscarTodosPersonagensPort {

    private final PersonagemJpaRepository personagemRepository;
    private final JutsuJpaRepository jutsuRepository;
    private final PersonagemMapper mapper;

    public PersonagemPersistenceAdapter(
            PersonagemJpaRepository personagemRepository, 
            JutsuJpaRepository jutsuRepository,
            PersonagemMapper mapper) {
        this.personagemRepository = personagemRepository;
        this.jutsuRepository = jutsuRepository;
        this.mapper = mapper;
    }

    @Override
    public Personagem salvar(Personagem personagem) {
        List<JutsuEntity> jutsusEntities = personagem.getJutsus().stream()
            .map(jutsu -> new JutsuEntity(jutsu.getNome(), jutsu.getCustoChakra()))
            .collect(Collectors.toList());
        jutsusEntities = jutsuRepository.saveAll(jutsusEntities);

        
        PersonagemEntity entity = mapper.toEntity(personagem);
        entity.setJutsus(jutsusEntities); 
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
        return personagemRepository.findById(id)
            .map(mapper::toDomain);
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
