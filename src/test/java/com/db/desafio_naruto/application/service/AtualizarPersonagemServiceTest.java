package com.db.desafio_naruto.application.service;

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

import com.db.desafio_naruto.application.port.in.command.AtualizarPersonagemCommand;
import com.db.desafio_naruto.application.port.out.AtualizarPersonagemPort;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;

@ExtendWith(MockitoExtension.class)
class AtualizarPersonagemServiceTest {

    @Mock
    private AtualizarPersonagemPort atualizarPersonagemPort;

    @Mock
    private BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;

    @InjectMocks
    private AtualizarPersonagemService service;

    private Personagem personagemExistente;
    private AtualizarPersonagemCommand command;

    @BeforeEach
    void setUp() {
        personagemExistente = new Personagem();
        personagemExistente.setId(1L);
        personagemExistente.setNome("Naruto");
        personagemExistente.setIdade(16);
        personagemExistente.setAldeia("Konoha");
        personagemExistente.setJutsus(Arrays.asList("Rasengan"));
        personagemExistente.setChakra(100);
        personagemExistente.setTipoNinja(TipoNinja.NINJUTSU);

        command = new AtualizarPersonagemCommand(
            1L,
            "Naruto Uzumaki",
            17,
            "Konoha",
            Arrays.asList("Rasengan", "Sage Mode"),
            150,
            TipoNinja.NINJUTSU
        );
    }

    @Test
    void deveAtualizarPersonagemComSucesso() {
        when(buscarPorIdPersonagemPort.buscarPorId(1L))
            .thenReturn(Optional.of(personagemExistente));
        
        Personagem personagemAtualizado = new Personagem();
        personagemAtualizado.setId(command.getId());
        personagemAtualizado.setNome(command.getNome());
        personagemAtualizado.setIdade(command.getIdade());
        personagemAtualizado.setAldeia(command.getAldeia());
        personagemAtualizado.setJutsus(command.getJutsus());
        personagemAtualizado.setChakra(command.getChakra());
        personagemAtualizado.setTipoNinja(command.getTipoNinja());

        when(atualizarPersonagemPort.atualizar(any(Personagem.class)))
            .thenReturn(personagemAtualizado);

        Personagem resultado = service.atualizar(command);

        assertNotNull(resultado);
        assertEquals(command.getId(), resultado.getId());
        assertEquals(command.getNome(), resultado.getNome());
        assertEquals(command.getIdade(), resultado.getIdade());
        assertEquals(command.getAldeia(), resultado.getAldeia());
        assertEquals(command.getJutsus(), resultado.getJutsus());
        assertEquals(command.getChakra(), resultado.getChakra());
        assertEquals(command.getTipoNinja(), resultado.getTipoNinja());

        verify(buscarPorIdPersonagemPort).buscarPorId(1L);
        verify(atualizarPersonagemPort).atualizar(any(Personagem.class));
    }

    @Test
    void deveLancarExcecaoQuandoPersonagemNaoEncontrado() {
        when(buscarPorIdPersonagemPort.buscarPorId(1L))
            .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> service.atualizar(command));

        assertEquals("Personagem não encontrado", exception.getMessage());
        verify(buscarPorIdPersonagemPort).buscarPorId(1L);
        verify(atualizarPersonagemPort, never()).atualizar(any());
    }
}