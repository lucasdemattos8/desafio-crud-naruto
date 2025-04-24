package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_naruto.application.port.in.SalvarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.command.AtualizarPersonagemCommand;
import com.db.desafio_naruto.application.port.in.command.CriarPersonagemCommand;
import com.db.desafio_naruto.application.port.in.dto.PersonagemDTO;
import com.db.desafio_naruto.application.port.out.UriBuilderPort;
import com.db.desafio_naruto.application.port.in.AtualizarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.BuscarPorIdPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.BuscarTodosPersonagensUseCase;
import com.db.desafio_naruto.application.port.in.DeletarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.ExecutarJutsuUseCase;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper.PersonagemPersistenceMapper;
import com.db.desafio_naruto.infrastructure.handler.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/personagens")
@Tag(name = "Personagens", description = "Endpoints para operações CRUD de Personagens")
public class PersonagemController {

    private final SalvarPersonagemUseCase createPersonagemUseCase;
    private final ExecutarJutsuUseCase executarJutsuUseCase;
    private final DeletarPersonagemUseCase deletarPersonagemUseCase;
    private final AtualizarPersonagemUseCase atualizarPersonagemUseCase;
    private final BuscarTodosPersonagensUseCase buscarTodosPersonagensUseCase;
    private final BuscarPorIdPersonagemUseCase buscarPorIdPersonagemUseCase;
    private final PersonagemPersistenceMapper personagemMapper;
    private final UriBuilderPort uriBuilder;

    public PersonagemController(
        SalvarPersonagemUseCase createPersonagemUseCase, ExecutarJutsuUseCase executarJutsuUseCase,
        DeletarPersonagemUseCase deletarPersonagemUseCase, AtualizarPersonagemUseCase atualizarPersonagemUseCase,
        BuscarTodosPersonagensUseCase buscarTodosPersonagensUseCase, PersonagemPersistenceMapper personagemMapper,
        BuscarPorIdPersonagemUseCase buscarPorIdPersonagemUseCase, UriBuilderPort uriBuilderPort) {
        this.createPersonagemUseCase = createPersonagemUseCase;
        this.executarJutsuUseCase = executarJutsuUseCase;
        this.deletarPersonagemUseCase = deletarPersonagemUseCase;
        this.atualizarPersonagemUseCase = atualizarPersonagemUseCase;
        this.buscarPorIdPersonagemUseCase = buscarPorIdPersonagemUseCase;
        this.buscarTodosPersonagensUseCase = buscarTodosPersonagensUseCase;
        this.personagemMapper = personagemMapper;
        this.uriBuilder = uriBuilderPort;
    }
    
    @Operation(summary = "Lista todos os personagens",
        description = "Retorna uma lista paginada de personagens com opções de ordenação")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de personagens recuperada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<PersonagemDTO>> buscarTodos(
            @Parameter(description = "Número da página (começando em 0)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de itens por página") 
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação (ex: id, nome, idade)") 
            @RequestParam(defaultValue = "id") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Personagem> personagens = buscarTodosPersonagensUseCase.buscarTodos(pageable);
        Page<PersonagemDTO> personagensDTO = personagemMapper.toDto(personagens);
        return ResponseEntity.ok(personagensDTO);
    }

    @Operation(summary = "Busca um personagem por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Personagem encontrado"),
        @ApiResponse(responseCode = "404", description = "Personagem não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonagemDTO> buscarPorID(
            @PathVariable Long id) {
        Personagem personagemDominio = buscarPorIdPersonagemUseCase.buscarPorID(id);

        PersonagemDTO personagemDTO = personagemMapper.toDto(personagemDominio);
        return ResponseEntity.ok().body(personagemDTO);
    }

    @Operation(summary = "Cria um novo personagem",
            description = "Adiciona um novo personagem ao universo Naruto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Personagem criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PersonagemDTO> criarPessoa(
            @Parameter(description = "Dados do novo personagem") 
            @RequestBody CriarPersonagemCommand personagem) {
        Personagem personagemSalvoDominio = createPersonagemUseCase.salvar(personagem);
        URI location = uriBuilder.buildUri("/{id}", personagemSalvoDominio.getId());

        return ResponseEntity.created(location).body(personagemMapper.toDto(personagemSalvoDominio));
    }

    @Operation(summary = "Executa um jutsu",
            description = "Faz um personagem executar seu jutsu característico com opção de desvio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jutsu executado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Personagem não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/jutsu")
    public ResponseEntity<String> executarJutsu(
            @Parameter(description = "ID do personagem") 
            @PathVariable Long id,
            @Parameter(description = "Indica se o personagem deve desviar do ataque") 
            @RequestParam(defaultValue = "false") boolean desviar) {
        String resultado = executarJutsuUseCase.executar(id, desviar);
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Atualiza um personagem",
              description = "Atualiza os dados de um personagem existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Personagem atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Personagem não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonagemDTO> atualizar(
            @Parameter(description = "ID do personagem") 
            @PathVariable Long id,
            @Parameter(description = "Novos dados do personagem") 
            @RequestBody AtualizarPersonagemCommand command) {
        command.setId(id);

        Personagem personagemDominio = atualizarPersonagemUseCase.atualizar(command);
        PersonagemDTO personagemDTO = personagemMapper.toDto(personagemDominio);

        return ResponseEntity.ok(personagemDTO);
    }

    @Operation(summary = "Deleta um personagem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Personagem deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Personagem não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "Acesso negado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarPersonagemUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

}