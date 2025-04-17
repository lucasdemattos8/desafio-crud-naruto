package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.PersonagemEntity;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.PersonagemPersistenceMapper;

@ExtendWith(MockitoExtension.class)
class PersonagemPersistenceAdapterTest {

    @Mock
    private PersonagemJpaRepository repository;

    @Mock
    private PersonagemPersistenceMapper mapper;

    @InjectMocks
    private PersonagemPersistenceAdapter adapter;

    private Personagem personagem;
    private PersonagemEntity entity;

    @BeforeEach
    void setUp() {
        personagem = new Personagem();
        personagem.setId(1L);
        personagem.setNome("Naruto");
        personagem.setIdade(16);
        personagem.setAldeia("Konoha");
        personagem.setJutsus(Arrays.asList("Rasengan"));
        personagem.setChakra(100);
        personagem.setTipoNinja(TipoNinja.NINJUTSU);

        entity = new PersonagemEntity();
        entity.setId(1L);
        entity.setNome("Naruto");
        entity.setIdade(16);
        entity.setAldeia("Konoha");
        entity.setJutsus(Arrays.asList("Rasengan"));
        entity.setChakra(100);
        entity.setTipoNinja(TipoNinja.NINJUTSU);
    }

    @Test
    void deveSalvarPersonagemComSucesso() {
        when(mapper.toEntity(personagem)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(personagem);

        Personagem resultado = adapter.salvar(personagem);

        assertNotNull(resultado);
        assertEquals(personagem.getId(), resultado.getId());
        assertEquals(personagem.getNome(), resultado.getNome());
        
        verify(mapper).toEntity(personagem);
        verify(repository).save(entity);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveBuscarTodosPersonagensComSucesso() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PersonagemEntity> entityPage = new PageImpl<>(Arrays.asList(entity));
        
        when(repository.findAll(pageable)).thenReturn(entityPage);
        when(mapper.toDomain(entity)).thenReturn(personagem);

        Page<Personagem> resultado = adapter.buscarTodos(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        assertEquals(personagem.getNome(), resultado.getContent().get(0).getNome());
        
        verify(repository).findAll(pageable);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(personagem);

        Optional<Personagem> resultado = adapter.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(personagem.getNome(), resultado.get().getNome());
        
        verify(repository).findById(1L);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveAtualizarPersonagemComSucesso() {
        when(mapper.toEntity(personagem)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(personagem);

        Personagem resultado = adapter.atualizar(personagem);

        assertNotNull(resultado);
        assertEquals(personagem.getNome(), resultado.getNome());
        
        verify(mapper).toEntity(personagem);
        verify(repository).save(entity);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveDeletarPersonagemComSucesso() {
        doNothing().when(repository).deleteById(1L);

        adapter.deletar(1L);

        verify(repository).deleteById(1L);
    }
}