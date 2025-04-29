package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_naruto.application.port.in.BatalhaUseCase;
import com.db.desafio_naruto.application.port.in.dto.batalha.AcaoBatalhaRequestDTO;
import com.db.desafio_naruto.application.port.in.dto.batalha.BatalhaResponseDTO;
import com.db.desafio_naruto.application.port.in.dto.batalha.IniciarBatalhaRequestDTO;
import com.db.desafio_naruto.infrastructure.handler.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/batalhas")
@Tag(name = "Batalhas", description = "Endpoints para gerenciar batalhas entre ninjas")
public class BatalhaController {

    private final BatalhaUseCase batalhaUseCase;

    public BatalhaController(BatalhaUseCase batalhaUseCase) {
        this.batalhaUseCase = batalhaUseCase;
    }

    @Operation(
        summary = "Inicia uma nova batalha entre dois ninjas",
        description = "Cria uma nova batalha entre dois ninjas existentes no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Batalha criada com sucesso",
            content = @Content(schema = @Schema(implementation = BatalhaResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Um dos ninjas não foi encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<BatalhaResponseDTO> iniciarBatalha(@RequestBody IniciarBatalhaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batalhaUseCase.iniciarBatalha(request));
    }

    @Operation(
        summary = "Executa uma ação em uma batalha",
        description = "Permite que um ninja execute uma ação (atacar ou desviar) durante seu turno"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ação executada com sucesso",
            content = @Content(schema = @Schema(implementation = BatalhaResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Batalha não encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Turno inválido ou ação não permitida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping("/{batalhaId}/acoes")
    public ResponseEntity<BatalhaResponseDTO> executarAcao(
            @Parameter(description = "ID da batalha") @PathVariable Long batalhaId,
            @Parameter(description = "Dados da ação a ser executada") @RequestBody AcaoBatalhaRequestDTO request) {
        return ResponseEntity.ok(batalhaUseCase.executarAcao(batalhaId, request));
    }

    @Operation(
        summary = "Consulta o estado atual de uma batalha",
        description = "Retorna informações detalhadas sobre o estado atual da batalha"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Consulta realizada com sucesso",
            content = @Content(schema = @Schema(implementation = BatalhaResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Batalha não encontrada",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @GetMapping("/{batalhaId}")
    public ResponseEntity<BatalhaResponseDTO> consultarBatalha(
            @Parameter(description = "ID da batalha") @PathVariable Long batalhaId) {
        return ResponseEntity.ok(batalhaUseCase.consultarBatalha(batalhaId));
    }
}