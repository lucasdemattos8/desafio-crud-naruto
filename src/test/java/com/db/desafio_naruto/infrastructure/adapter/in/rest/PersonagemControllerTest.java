package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.db.desafio_naruto.application.port.in.*;
import com.db.desafio_naruto.application.port.in.command.AtualizarPersonagemCommand;
import com.db.desafio_naruto.application.port.in.dto.PersonagemDTO;
import com.db.desafio_naruto.application.port.out.UriBuilderPort;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.PersonagemPersistenceMapper;

@ExtendWith(MockitoExtension.class)
class PersonagemControllerTest {

    @Mock
    private SalvarPersonagemUseCase salvarPersonagemUseCase;
    
    @Mock
    private ExecutarJutsuUseCase executarJutsuUseCase;
    
    @Mock
    private DeletarPersonagemUseCase deletarPersonagemUseCase;
    
    @Mock
    private AtualizarPersonagemUseCase atualizarPersonagemUseCase;
    
    @Mock
    private BuscarTodosPersonagensUseCase buscarTodosPersonagensUseCase;
    
    @Mock
    private PersonagemPersistenceMapper personagemMapper;

    @Mock
    private UriBuilderPort uriBuilderPort;

    @InjectMocks
    private PersonagemController controller;

    private Personagem personagem;
    private PersonagemDTO personagemDTO;

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

        personagemDTO = new PersonagemDTO(
            1L, "Naruto", 16, "Konoha", 
            Arrays.asList("Rasengan"), 100, 
            TipoNinja.NINJUTSU
        );
    }

    @SuppressWarnings("null")
    @Test
    void deveBuscarTodosPersonagensComSucesso() {
        Page<Personagem> personagemPage = new PageImpl<>(Arrays.asList(personagem));
        Page<PersonagemDTO> dtoPage = new PageImpl<>(Arrays.asList(personagemDTO));
        
        when(buscarTodosPersonagensUseCase.buscarTodos(any(Pageable.class)))
            .thenReturn(personagemPage);
        when(personagemMapper.toDto(personagemPage))
            .thenReturn(dtoPage);

        ResponseEntity<Page<PersonagemDTO>> response = controller.buscarTodos(0, 10, "id");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals("Naruto", response.getBody().getContent().get(0).getNome());
        
        verify(buscarTodosPersonagensUseCase).buscarTodos(any(Pageable.class));
    }

    @SuppressWarnings("null")
    @Test
    void deveSalvarPersonagemComSucesso() throws URISyntaxException {
        URI expectedUri = new URI("http://localhost:8080/api/v1/personagens/1");
        when(salvarPersonagemUseCase.salvar(personagem))
            .thenReturn(personagem);
        when(personagemMapper.toDto(personagem))
            .thenReturn(personagemDTO);
        when(uriBuilderPort.buildUri(anyString(), any()))
            .thenReturn(expectedUri);

        ResponseEntity<PersonagemDTO> response = controller.createPerson(personagem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedUri, response.getHeaders().getLocation());
        assertNotNull(response.getBody());
        assertEquals("Naruto", response.getBody().getNome());
        
        verify(salvarPersonagemUseCase).salvar(personagem);
        verify(personagemMapper).toDto(personagem);
        verify(uriBuilderPort).buildUri("/{id}", 1L);
    }

    @Test
    void deveExecutarJutsuComSucesso() {
        when(executarJutsuUseCase.executar(1L, false))
            .thenReturn("Naruto está usando um jutsu!");

        ResponseEntity<String> response = controller.executarJutsu(1L, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Naruto está usando um jutsu!", response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    void deveAtualizarPersonagemComSucesso() {
        AtualizarPersonagemCommand command = new AtualizarPersonagemCommand(
            1L, "Naruto", 17, "Konoha", 
            Arrays.asList("Rasengan"), 100, 
            TipoNinja.NINJUTSU
        );

        when(atualizarPersonagemUseCase.atualizar(command))
            .thenReturn(personagem);
        when(personagemMapper.toDto(personagem))
            .thenReturn(personagemDTO);

        ResponseEntity<PersonagemDTO> response = controller.atualizar(1L, command);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Naruto", response.getBody().getNome());
    }

    @Test
    void deveDeletarPersonagemComSucesso() {
        doNothing().when(deletarPersonagemUseCase).deletar(1L);

        ResponseEntity<Void> response = controller.deletar(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deletarPersonagemUseCase).deletar(1L);
    }
}