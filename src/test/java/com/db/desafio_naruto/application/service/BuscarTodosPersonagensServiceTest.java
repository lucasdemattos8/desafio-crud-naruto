package com.db.desafio_naruto.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

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

import com.db.desafio_naruto.application.port.out.BuscarTodosPersonagensPort;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;

@ExtendWith(MockitoExtension.class)
public class BuscarTodosPersonagensServiceTest {
    
    @Mock
    private BuscarTodosPersonagensPort buscarTodosPersonagensPort;

    @InjectMocks
    private BuscarTodosPersonagensService service;

    private List<Personagem> personagens;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        Personagem naruto = new Personagem();
        naruto.setId(1L);
        naruto.setNome("Naruto");
        naruto.setIdade(16);
        naruto.setAldeia("Konoha");
        naruto.setJutsus(Arrays.asList("Rasengan"));
        naruto.setChakra(100);
        naruto.setTipoNinja(TipoNinja.NINJUTSU);

        Personagem sasuke = new Personagem();
        sasuke.setId(2L);
        sasuke.setNome("Sasuke");
        sasuke.setIdade(16);
        sasuke.setAldeia("Konoha");
        sasuke.setJutsus(Arrays.asList("Chidori"));
        sasuke.setChakra(100);
        sasuke.setTipoNinja(TipoNinja.NINJUTSU);

        personagens = Arrays.asList(naruto, sasuke);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void deveBuscarTodosPersonagensComPaginacao() {
        Page<Personagem> page = new PageImpl<>(personagens, pageable, personagens.size());
        when(buscarTodosPersonagensPort.buscarTodos(pageable)).thenReturn(page);

        Page<Personagem> resultado = service.buscarTodos(pageable);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals(1L, resultado.getContent().get(0).getId());
        assertEquals("Naruto", resultado.getContent().get(0).getNome());
        assertEquals(2L, resultado.getContent().get(1).getId());
        assertEquals("Sasuke", resultado.getContent().get(1).getNome());
        
        verify(buscarTodosPersonagensPort).buscarTodos(pageable);
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoHaPersonagens() {
        Page<Personagem> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(buscarTodosPersonagensPort.buscarTodos(pageable)).thenReturn(emptyPage);

        Page<Personagem> resultado = service.buscarTodos(pageable);

        assertNotNull(resultado);
        assertTrue(resultado.getContent().isEmpty());
        assertEquals(0, resultado.getTotalElements());
        
        verify(buscarTodosPersonagensPort).buscarTodos(pageable);
    }
}