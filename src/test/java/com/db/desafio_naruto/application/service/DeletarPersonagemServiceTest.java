package com.db.desafio_naruto.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.DeletarPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;

@ExtendWith(MockitoExtension.class)
public class DeletarPersonagemServiceTest {
    
    @Mock
    private DeletarPersonagemPort deletarPersonagemPort;
    
    @Mock
    private BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;
    
    @InjectMocks
    private DeletarPersonagemService service;
    
    @Test
    void deveDeletarPersonagemQuandoExistir() {
        Long id = 1L;
        Personagem personagem = new Personagem();
        personagem.setId(id);
        
        when(buscarPorIdPersonagemPort.buscarPorId(id))
            .thenReturn(Optional.of(personagem));
        
        doNothing().when(deletarPersonagemPort).deletar(id);
        
        assertDoesNotThrow(() -> service.deletar(id));
        
        verify(buscarPorIdPersonagemPort).buscarPorId(id);
        verify(deletarPersonagemPort).deletar(id);
    }
    
    @Test
    void deveLancarExcecaoQuandoPersonagemNaoExistir() {
        Long id = 1L;
        when(buscarPorIdPersonagemPort.buscarPorId(id))
            .thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> service.deletar(id));
        
        assertEquals("Personagem com ID 1 não encontrado!", exception.getMessage());
        verify(buscarPorIdPersonagemPort).buscarPorId(id);
        verify(deletarPersonagemPort, never()).deletar(anyLong());
    }
}