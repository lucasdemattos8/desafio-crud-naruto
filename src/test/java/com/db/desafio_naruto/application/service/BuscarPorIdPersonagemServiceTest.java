package com.db.desafio_naruto.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class BuscarPorIdPersonagemServiceTest {

    @Mock
    private BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;

    @Mock
    private LogPort logPort;

    @InjectMocks
    private BuscarPorIdPersonagemService service;

    private Personagem personagem;

    @BeforeEach
    void setUp() {
        personagem = new Personagem();
        personagem.setId(1L);
        personagem.setNome("Naruto");
        personagem.setIdade(16);
        personagem.setAldeia("Konoha");
        personagem.setChakra(100);
        personagem.setTipoNinja(TipoNinja.NINJUTSU);
    }

    @Test
    void deveBuscarPersonagemPorIdComSucesso() {
        when(buscarPorIdPersonagemPort.buscarPorId(1L))
            .thenReturn(Optional.of(personagem));

        Personagem resultado = service.buscarPorID(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Naruto", resultado.getNome());
        assertEquals(16, resultado.getIdade());
        assertEquals("Konoha", resultado.getAldeia());
        assertEquals(100, resultado.getChakra());
        assertEquals(TipoNinja.NINJUTSU, resultado.getTipoNinja());

        verify(buscarPorIdPersonagemPort).buscarPorId(1L);
        verify(logPort).info("Iniciando busca de personagem por ID: {}", 1L);
        verify(logPort).debug("Personagem encontrado: {} (ID: {})", "Naruto", 1L);
    }

    @Test
    void deveLancarExcecaoQuandoPersonagemNaoEncontrado() {
        Long idInexistente = 999L;
        when(buscarPorIdPersonagemPort.buscarPorId(idInexistente))
            .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> service.buscarPorID(idInexistente)
        );

        assertEquals("Personagem com ID " + idInexistente + " não encontrado!", exception.getMessage());
        verify(buscarPorIdPersonagemPort).buscarPorId(idInexistente);
        verify(logPort).info("Iniciando busca de personagem por ID: {}", idInexistente);
        verify(logPort).error("Personagem não encontrado com ID: {}", idInexistente);
    }

    @Test
    void deveLancarELogarErroInesperado() {
        Long id = 1L;
        when(buscarPorIdPersonagemPort.buscarPorId(id))
            .thenThrow(new RuntimeException("Erro inesperado"));

        assertThrows(RuntimeException.class, () -> service.buscarPorID(id));

        verify(logPort).info("Iniciando busca de personagem por ID: {}", id);
        verify(logPort).error(eq("Erro ao buscar personagem com ID: {}"), eq(id), any(RuntimeException.class));
    }
}