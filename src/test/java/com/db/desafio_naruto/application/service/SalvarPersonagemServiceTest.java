package com.db.desafio_naruto.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    
    private Personagem personagem;
    
    @BeforeEach
    void setUp() {
        personagem = new Personagem();
        personagem.setNome("Naruto");
        personagem.setIdade(16);
        personagem.setAldeia("Konoha");
        personagem.setJutsus(Arrays.asList("Rasengan", "Kage Bunshin"));
        personagem.setChakra(100);
        personagem.setTipoNinja(TipoNinja.NINJUTSU);
    }
    
    @Test
    void deveSalvarPersonagemComSucesso() {
        Personagem personagemSalvo = new Personagem();
        personagemSalvo.setId(1L);
        personagemSalvo.setNome(personagem.getNome());
        personagemSalvo.setIdade(personagem.getIdade());
        personagemSalvo.setAldeia(personagem.getAldeia());
        personagemSalvo.setJutsus(personagem.getJutsus());
        personagemSalvo.setChakra(personagem.getChakra());
        personagemSalvo.setTipoNinja(personagem.getTipoNinja());
        
        when(salvarPersonagemPort.salvar(any(Personagem.class)))
            .thenReturn(personagemSalvo);
        
        Personagem resultado = service.salvar(personagem);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Naruto", resultado.getNome());
        assertEquals(16, resultado.getIdade());
        assertEquals("Konoha", resultado.getAldeia());
        assertEquals(Arrays.asList("Rasengan", "Kage Bunshin"), resultado.getJutsus());
        assertEquals(100, resultado.getChakra());
        assertEquals(TipoNinja.NINJUTSU, resultado.getTipoNinja());
        
        verify(salvarPersonagemPort).salvar(any(Personagem.class));
        verify(logPort).info(eq("Iniciando salvamento do personagem: {}"), eq("Naruto"));
        verify(logPort).debug(eq("Personagem salvo com sucesso: {}"), eq(1L));
    }

    @Test
    void deveLancarExcecaoELogarErro() {
        when(salvarPersonagemPort.salvar(any(Personagem.class)))
            .thenThrow(new RuntimeException("Erro ao salvar"));

        assertThrows(RuntimeException.class, () -> service.salvar(personagem));
        
        verify(logPort).info(eq("Iniciando salvamento do personagem: {}"), eq("Naruto"));
        verify(logPort).error(eq("Erro ao salvar personagem: {}"), eq("Naruto"), any(RuntimeException.class));
    }
}