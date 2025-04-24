package com.db.desafio_naruto.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.db.desafio_naruto.application.port.in.command.CriarPersonagemCommand;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.application.port.out.SalvarPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;

@ExtendWith(MockitoExtension.class)
class SalvarPersonagemServiceTest {
    
    @Mock
    private SalvarPersonagemPort salvarPersonagemPort;
    
    @Mock
    private LogPort logPort;
    
    @InjectMocks
    private SalvarPersonagemService service;
    
    private CriarPersonagemCommand command;
    
    @BeforeEach
    void setUp() {
        command = new CriarPersonagemCommand(
            null,
            "Naruto Uzumaki",
            17,
            "Konoha",
            Arrays.asList("Rasengan", "Sage Mode"),
            150,
            TipoNinja.NINJUTSU
        );
    }
    
    @Test
    void deveSalvarPersonagemComSucesso() {
        Personagem personagemSalvo = new Personagem();
        personagemSalvo.setId(1L);
        personagemSalvo.setNome(command.getNome());
        personagemSalvo.setIdade(command.getIdade());
        personagemSalvo.setAldeia(command.getAldeia());
        personagemSalvo.setJutsus(command.getJutsus());
        personagemSalvo.setChakra(command.getChakra());
        personagemSalvo.setTipoNinja(command.getTipoNinja());
        
        when(salvarPersonagemPort.salvar(any(Personagem.class)))
            .thenReturn(personagemSalvo);
        
        Personagem resultado = service.salvar(command);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(command.getNome(), resultado.getNome());
        assertEquals(command.getIdade(), resultado.getIdade());
        assertEquals(command.getAldeia(), resultado.getAldeia());
        assertEquals(command.getJutsus(), resultado.getJutsus());
        assertEquals(command.getChakra(), resultado.getChakra());
        assertEquals(command.getTipoNinja(), resultado.getTipoNinja());
        
        verify(salvarPersonagemPort).salvar(any(Personagem.class));
        verify(logPort).info(eq("Iniciando salvamento do personagem: {}"), eq(command.getNome()));
        verify(logPort).debug(eq("Personagem salvo com sucesso: {}"), eq(1L));
    }

    @Test
    void deveLancarExcecaoELogarErro() {
        when(salvarPersonagemPort.salvar(any(Personagem.class)))
            .thenThrow(new RuntimeException("Erro ao salvar"));

        assertThrows(RuntimeException.class, () -> service.salvar(command));
        
        verify(logPort).info(eq("Iniciando salvamento do personagem: {}"), eq(command.getNome()));
        verify(logPort).error(eq("Erro ao salvar personagem: {}"), eq(command.getNome()), any(RuntimeException.class));
    }
}