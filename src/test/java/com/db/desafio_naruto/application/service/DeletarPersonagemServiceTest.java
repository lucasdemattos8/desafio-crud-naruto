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
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.domain.model.Personagem;

@ExtendWith(MockitoExtension.class)
public class DeletarPersonagemServiceTest {
    
    @Mock
    private DeletarPersonagemPort deletarPersonagemPort;
    
    @Mock
    private BuscarPorIdPersonagemPort buscarPorIdPersonagemPort;
    
    @Mock
    private LogPort logPort;
    
    @InjectMocks
    private DeletarPersonagemService service;
    
    @Test
    void deveDeletarPersonagemQuandoExistir() {
        Long id = 1L;
        Personagem personagem = new Personagem();
        personagem.setId(id);
        personagem.setNome("Naruto");
        
        when(buscarPorIdPersonagemPort.buscarPorId(id))
            .thenReturn(Optional.of(personagem));
        
        doNothing().when(deletarPersonagemPort).deletar(id);
        
        assertDoesNotThrow(() -> service.deletar(id));
        
        verify(logPort).info("Iniciando processo de deleção do personagem ID: {}", id);
        verify(logPort).debug("Personagem encontrado para deleção: {} (ID: {})", personagem.getNome(), id);
        verify(logPort).info("Personagem deletado com sucesso: {} (ID: {})", personagem.getNome(), id);
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
        verify(logPort).info("Iniciando processo de deleção do personagem ID: {}", id);
        verify(logPort).error("Tentativa de deletar personagem inexistente. ID: {}", id);
        verify(buscarPorIdPersonagemPort).buscarPorId(id);
        verify(deletarPersonagemPort, never()).deletar(anyLong());
    }

    @Test
    void deveLancarExcecaoQuandoErroNaDelecao() {
        Long id = 1L;
        Personagem personagem = new Personagem();
        personagem.setId(id);
        personagem.setNome("Naruto");
        
        when(buscarPorIdPersonagemPort.buscarPorId(id))
            .thenReturn(Optional.of(personagem));
        
        doThrow(new RuntimeException("Erro ao deletar"))
            .when(deletarPersonagemPort).deletar(id);
        
        assertThrows(RuntimeException.class, () -> service.deletar(id));
        
        verify(logPort).info("Iniciando processo de deleção do personagem ID: {}", id);
        verify(logPort).debug("Personagem encontrado para deleção: {} (ID: {})", personagem.getNome(), id);
        verify(logPort).error(eq("Erro ao deletar personagem ID: {}"), eq(id), any(RuntimeException.class));
        verify(buscarPorIdPersonagemPort).buscarPorId(id);
        verify(deletarPersonagemPort).deletar(id);
    }
}