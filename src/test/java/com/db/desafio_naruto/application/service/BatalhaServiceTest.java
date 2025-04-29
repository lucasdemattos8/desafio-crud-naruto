package com.db.desafio_naruto.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.db.desafio_naruto.application.port.in.dto.batalha.AcaoBatalhaRequestDTO;
import com.db.desafio_naruto.application.port.in.dto.batalha.BatalhaResponseDTO;
import com.db.desafio_naruto.application.port.in.dto.personagem.PersonagemBatalhaDTO;
import com.db.desafio_naruto.application.port.in.dto.personagem.PersonagemDTO;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.application.port.out.SalvarBatalhaPort;
import com.db.desafio_naruto.application.service.exceptions.TurnoInvalidoException;
import com.db.desafio_naruto.domain.model.Batalha;
import com.db.desafio_naruto.domain.model.Jutsu;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoAcao;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.PersonagemMapper;

@ExtendWith(MockitoExtension.class)
public class BatalhaServiceTest {
    @Mock
    private BuscarPorIdPersonagemPort personagemPort;
    
    @Mock
    private SalvarBatalhaPort batalhaPort;
    
    @Mock
    private LogPort logPort;
    
    @Mock
    private PersonagemMapper personagemMapper;

    @InjectMocks
    private BatalhaService service;

    private Personagem ninja1;
    private Personagem ninja2;
    private Batalha batalha;
    private PersonagemBatalhaDTO ninja1Dto;
    private PersonagemBatalhaDTO ninja2Dto;

    @BeforeEach
    void setUp() {
        ninja1 = new Personagem();
        ninja1.setId(1L);
        ninja1.setNome("Naruto");
        ninja1.setChakra(100);
        ninja1.setTipoNinja(TipoNinja.NINJUTSU);
        ninja1.setJutsus(Arrays.asList(
            new Jutsu(1L, "Rasengan", 20),
            new Jutsu(2L, "Kage Bunshin", 10)
        ));

        ninja2 = new Personagem();
        ninja2.setId(2L);
        ninja2.setNome("Sasuke");
        ninja2.setChakra(100);
        ninja2.setTipoNinja(TipoNinja.NINJUTSU);
        ninja2.setJutsus(Arrays.asList(
            new Jutsu(3L, "Chidori", 25),
            new Jutsu(4L, "Sharingan", 15)
        ));

        batalha = new Batalha(1L, ninja1, ninja2, false, 1, ninja1.getId());
        
        ninja1Dto = new PersonagemBatalhaDTO(1L, "Naruto", 
            Arrays.asList("Rasengan", "Kage Bunshin"), 100, 100, TipoNinja.NINJUTSU);
        ninja2Dto = new PersonagemBatalhaDTO(2L, "Sasuke", 
            Arrays.asList("Chidori", "Sharingan"), 100, 100, TipoNinja.NINJUTSU);
    }

    @Test
    void deveConsultarBatalhaEmAndamento() {
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(personagemMapper.toBatalhaEstadoDto(ninja1)).thenReturn(ninja1Dto);
        when(personagemMapper.toBatalhaEstadoDto(ninja2)).thenReturn(ninja2Dto);

        BatalhaResponseDTO response = service.consultarBatalha(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertFalse(response.finalizada());
        assertEquals(1, response.turnoAtual());
        assertEquals(ninja1.getId(), response.ninjaAtual());
        assertTrue(response.mensagem().contains("Turno 1 - Vez de Naruto"));
        
        verify(batalhaPort).buscarPorId(1L);
        verify(personagemMapper, times(2)).toBatalhaEstadoDto(any(Personagem.class));
    }

    @Test
    void deveConsultarBatalhaFinalizada() {
        batalha.setFinalizada(true);
        batalha.setVidaNinja2(0);
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(personagemMapper.toBatalhaEstadoDto(ninja1)).thenReturn(ninja1Dto);
        when(personagemMapper.toBatalhaEstadoDto(ninja2)).thenReturn(ninja2Dto);

        BatalhaResponseDTO response = service.consultarBatalha(1L);

        assertNotNull(response);
        assertTrue(response.finalizada());
        assertTrue(response.mensagem().contains("Batalha finalizada! Vencedor: Naruto"));
        
        verify(batalhaPort).buscarPorId(1L);
    }
    
    @Test
    void deveLancarExcecaoAoJogarEmTurnoInvalido() {
        var request = new AcaoBatalhaRequestDTO(2L, TipoAcao.USAR_JUTSU, "Chidori");
        batalha.setNinjaAtual(1L);
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));

        assertThrows(TurnoInvalidoException.class, 
            () -> service.executarAcao(1L, request));
            
        verify(batalhaPort).buscarPorId(1L);
    }
    
    @Test
    void deveExecutarJutsuComSucesso() {
        var request = new AcaoBatalhaRequestDTO(1L, TipoAcao.USAR_JUTSU, "Rasengan");
        
        PersonagemDTO ninja1PersonagemDto = mock(PersonagemDTO.class);
        PersonagemDTO ninja2PersonagemDto = mock(PersonagemDTO.class);
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(batalhaPort.salvar(any(Batalha.class))).thenReturn(batalha);
        when(personagemMapper.toBatalhaEstadoDto(any(Personagem.class))).thenReturn(ninja1Dto, ninja2Dto);
        
        when(personagemMapper.toDto(ninja1)).thenReturn(ninja1PersonagemDto);
        when(personagemMapper.toDto(ninja2)).thenReturn(ninja2PersonagemDto);

        BatalhaResponseDTO response = service.executarAcao(1L, request);

        assertNotNull(response);
        assertFalse(response.finalizada());
        assertTrue(response.mensagem().contains("Naruto está preparando Rasengan"));
        assertTrue(batalha.temAtaquePendente());
        
        verify(batalhaPort).buscarPorId(1L);
        verify(batalhaPort, times(2)).salvar(batalha);
        verify(ninja1PersonagemDto).setChakra(anyInt());
        verify(ninja2PersonagemDto).setChakra(anyInt());
    }
    
    @Test
    void deveDesviarDeAtaqueComSucesso() {
        batalha.registrarAtaquePendente(2L, "Chidori", 40);
        batalha.setNinjaAtual(1L);
        
        var request = new AcaoBatalhaRequestDTO(1L, TipoAcao.DESVIAR, null);
        
        PersonagemDTO ninja1PersonagemDto = mock(PersonagemDTO.class);
        PersonagemDTO ninja2PersonagemDto = mock(PersonagemDTO.class);
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(batalhaPort.salvar(any(Batalha.class))).thenReturn(batalha);
        when(personagemMapper.toBatalhaEstadoDto(any(Personagem.class))).thenReturn(ninja1Dto, ninja2Dto);
        when(personagemMapper.toDto(ninja1)).thenReturn(ninja1PersonagemDto);
        when(personagemMapper.toDto(ninja2)).thenReturn(ninja2PersonagemDto);
        
        
        BatalhaResponseDTO response = service.executarAcao(1L, request);

        assertNotNull(response);
        assertFalse(response.finalizada());
        assertTrue(response.mensagem().contains("conseguiu desviar") || 
                  response.mensagem().contains("não conseguiu desviar"));
        assertFalse(batalha.temAtaquePendente());
        
        verify(batalhaPort).buscarPorId(1L);
        verify(batalhaPort, times(2)).salvar(batalha);
        verify(ninja1PersonagemDto).setChakra(anyInt());
        verify(ninja2PersonagemDto).setChakra(anyInt());
    }
    
    @Test
    void deveTentarDesviarSemAtaquePendente() {
        var request = new AcaoBatalhaRequestDTO(1L, TipoAcao.DESVIAR, null);
        
        PersonagemDTO ninja1PersonagemDto = mock(PersonagemDTO.class);
        PersonagemDTO ninja2PersonagemDto = mock(PersonagemDTO.class);
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(batalhaPort.salvar(any(Batalha.class))).thenReturn(batalha);
        when(personagemMapper.toBatalhaEstadoDto(any(Personagem.class))).thenReturn(ninja1Dto, ninja2Dto);
        when(personagemMapper.toDto(ninja1)).thenReturn(ninja1PersonagemDto);
        when(personagemMapper.toDto(ninja2)).thenReturn(ninja2PersonagemDto);
        
        BatalhaResponseDTO response = service.executarAcao(1L, request);

        assertNotNull(response);
        assertTrue(response.mensagem().contains("não havia nenhum ataque pendente"));
        
        verify(batalhaPort).buscarPorId(1L);
        verify(batalhaPort, times(2)).salvar(batalha);
        verify(ninja1PersonagemDto).setChakra(anyInt());
        verify(ninja2PersonagemDto).setChakra(anyInt());
    }
    
    @Test
    void deveExecutarJutsuSemChakraSuficiente() {
        ninja1.setChakra(5);
        batalha.setChackraNinja1(5);
        
        var request = new AcaoBatalhaRequestDTO(1L, TipoAcao.USAR_JUTSU, "Rasengan");
        
        PersonagemDTO ninja1PersonagemDto = mock(PersonagemDTO.class);
        PersonagemDTO ninja2PersonagemDto = mock(PersonagemDTO.class);
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(batalhaPort.salvar(any(Batalha.class))).thenReturn(batalha);
        when(personagemMapper.toBatalhaEstadoDto(any(Personagem.class))).thenReturn(ninja1Dto, ninja2Dto);
        when(personagemMapper.toDto(ninja1)).thenReturn(ninja1PersonagemDto);
        when(personagemMapper.toDto(ninja2)).thenReturn(ninja2PersonagemDto);
        
        BatalhaResponseDTO response = service.executarAcao(1L, request);

        assertNotNull(response);
        assertTrue(response.mensagem().contains("não tem chakra suficiente"));
        
        verify(batalhaPort).buscarPorId(1L);
        verify(batalhaPort, times(2)).salvar(batalha);
        verify(ninja1PersonagemDto).setChakra(anyInt());
        verify(ninja2PersonagemDto).setChakra(anyInt());
    }
    
    @Test
    void deveTentarUsarJutsuQuandoExisteAtaquePendente() {
        batalha.registrarAtaquePendente(2L, "Chidori", 40);
        batalha.setNinjaAtual(1L);
        
        var request = new AcaoBatalhaRequestDTO(1L, TipoAcao.USAR_JUTSU, "Rasengan");
        
        PersonagemDTO ninja1PersonagemDto = mock(PersonagemDTO.class);
        PersonagemDTO ninja2PersonagemDto = mock(PersonagemDTO.class);
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(batalhaPort.salvar(any(Batalha.class))).thenReturn(batalha);
        when(personagemMapper.toBatalhaEstadoDto(any(Personagem.class))).thenReturn(ninja1Dto, ninja2Dto);
        when(personagemMapper.toDto(ninja1)).thenReturn(ninja1PersonagemDto);
        when(personagemMapper.toDto(ninja2)).thenReturn(ninja2PersonagemDto);
        
        BatalhaResponseDTO response = service.executarAcao(1L, request);

        assertNotNull(response);
        assertTrue(response.mensagem().contains("tentou usar Rasengan"));
        assertTrue(response.mensagem().contains("foi atingido por Chidori"));
        assertFalse(batalha.temAtaquePendente());
        
        verify(batalhaPort).buscarPorId(1L);
        verify(batalhaPort, times(2)).salvar(batalha);
        verify(ninja1PersonagemDto).setChakra(anyInt());
        verify(ninja2PersonagemDto).setChakra(anyInt());
    }
    
    @Test
    void deveFinalizarBatalhaQuandoNinjaPerderTodaVida() {
        batalha.setVidaNinja2(30);
        batalha.registrarAtaquePendente(1L, "Rasengan", 40);
        batalha.setNinjaAtual(2L);
        
        var request = new AcaoBatalhaRequestDTO(2L, TipoAcao.DESVIAR, null);
        
        PersonagemDTO ninja1PersonagemDto = mock(PersonagemDTO.class);
        PersonagemDTO ninja2PersonagemDto = mock(PersonagemDTO.class);
        
        ReflectionTestUtils.setField(service, "random", new Random() {
            @Override
            public double nextDouble() {
                return 1.0;
            }
            
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        });
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(batalhaPort.salvar(any(Batalha.class))).thenAnswer(invocation -> {
            Batalha b = invocation.getArgument(0);
            if (b.getVidaNinja2() <= 0) {
                b.setFinalizada(true);
            }
            return b;
        });
        when(personagemMapper.toBatalhaEstadoDto(any(Personagem.class))).thenReturn(ninja1Dto, ninja2Dto);
        when(personagemMapper.toDto(ninja1)).thenReturn(ninja1PersonagemDto);
        when(personagemMapper.toDto(ninja2)).thenReturn(ninja2PersonagemDto);
        
        BatalhaResponseDTO response = service.executarAcao(1L, request);

        assertNotNull(response);
        assertTrue(batalha.isFinalizada());
        assertTrue(response.mensagem().contains("não conseguiu desviar"));
        assertEquals(0, batalha.getVidaNinja2());
        
        verify(batalhaPort).buscarPorId(1L);
        verify(batalhaPort, atLeastOnce()).salvar(batalha);
        verify(ninja1PersonagemDto).setChakra(anyInt());
        verify(ninja2PersonagemDto).setChakra(anyInt());
    }
    
    @Test
    void deveConsultarBatalhaComAtaquePendente() {
        batalha.registrarAtaquePendente(1L, "Rasengan", 40);
        
        when(batalhaPort.buscarPorId(1L)).thenReturn(Optional.of(batalha));
        when(personagemMapper.toBatalhaEstadoDto(ninja1)).thenReturn(ninja1Dto);
        when(personagemMapper.toBatalhaEstadoDto(ninja2)).thenReturn(ninja2Dto);

        BatalhaResponseDTO response = service.consultarBatalha(1L);

        assertNotNull(response);
        assertFalse(response.finalizada());
        assertTrue(response.mensagem().contains("Naruto está preparando Rasengan"));
        assertTrue(response.mensagem().contains("Sasuke pode tentar desviar"));
        
        verify(batalhaPort).buscarPorId(1L);
    }
}