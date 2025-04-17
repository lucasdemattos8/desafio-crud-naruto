package com.db.desafio_naruto.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;

@ExtendWith(MockitoExtension.class)
class ExecutarJutsuServiceTest {

    @Mock
    private BuscarPorIdPersonagemPort personagemPort;

    @InjectMocks
    private ExecutarJutsuService service;

    private Personagem personagem;

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
    }

    @Test
    void deveExecutarJutsuNinjutsuComSucesso() {
        when(personagemPort.buscarPorId(1L)).thenReturn(Optional.of(personagem));
        
        String resultado = service.executar(1L, false);

        assertEquals("Naruto está usando um jutsu de Ninjutsu!", resultado);
        verify(personagemPort).buscarPorId(1L);
    }

    @Test
    void deveDesviarNinjutsuComSucesso() {
        when(personagemPort.buscarPorId(1L)).thenReturn(Optional.of(personagem));

        String resultado = service.executar(1L, true);

        assertEquals("Naruto está desviando do ataque utilizando um jutsu de Ninjutsu!", resultado);
        verify(personagemPort).buscarPorId(1L);
    }

    @Test
    void deveExecutarJutsuGenjutsuComSucesso() {
        personagem.setNome("Itachi");
        personagem.setTipoNinja(TipoNinja.GENJUTSU);
        when(personagemPort.buscarPorId(1L)).thenReturn(Optional.of(personagem));
        
        String resultado = service.executar(1L, false);

        assertEquals("Itachi está usando um jutsu de Genjutsu!", resultado);
        verify(personagemPort).buscarPorId(1L);
    }

    @Test
    void deveDesviarGenjutsuComSucesso() {
        personagem.setNome("Itachi");
        personagem.setTipoNinja(TipoNinja.GENJUTSU);
        when(personagemPort.buscarPorId(1L)).thenReturn(Optional.of(personagem));

        String resultado = service.executar(1L, true);

        assertEquals("Itachi está desviando do ataque usando sua habilidade em Genjutsu!", resultado);
        verify(personagemPort).buscarPorId(1L);
    }

    @Test
    void deveExecutarJutsuTaijutsuComSucesso() {
        personagem.setNome("Might Guy");
        personagem.setTipoNinja(TipoNinja.TAIJUTSU);
        when(personagemPort.buscarPorId(1L)).thenReturn(Optional.of(personagem));
        
        String resultado = service.executar(1L, false);

        assertEquals("Might Guy está usando um golpe de Taijutsu!", resultado);
        verify(personagemPort).buscarPorId(1L);
    }

    @Test
    void deveDesviarTaijutsuComSucesso() {
        personagem.setNome("Might Guy");
        personagem.setTipoNinja(TipoNinja.TAIJUTSU);
        when(personagemPort.buscarPorId(1L)).thenReturn(Optional.of(personagem));

        String resultado = service.executar(1L, true);

        assertEquals("Might Guy está desviando utilizando sua habilidade em Taijutsu!", resultado);
        verify(personagemPort).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoPersonagemNaoEncontrado() {
        when(personagemPort.buscarPorId(1L)).thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.executar(1L, false));
        assertEquals("Personagem não encontrado", exception.getMessage());
        verify(personagemPort).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTipoNinjaInvalido() {
        personagem.setTipoNinja(null);
        when(personagemPort.buscarPorId(1L)).thenReturn(Optional.of(personagem));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> service.executar(1L, false));
        assertEquals("Tipo de ninja não pode ser null", exception.getMessage());
        verify(personagemPort).buscarPorId(1L);
    }
}