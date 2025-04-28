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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/batalhas")
@Tag(name = "Batalhas", description = "Endpoints para gerenciar batalhas entre ninjas")
public class BatalhaController {

    private final BatalhaUseCase batalhaUseCase;

    public BatalhaController(BatalhaUseCase batalhaUseCase) {
        this.batalhaUseCase = batalhaUseCase;
    }

    @PostMapping
    @Operation(summary = "Inicia uma nova batalha entre dois ninjas")
    public ResponseEntity<BatalhaResponseDTO> iniciarBatalha(
            @RequestBody IniciarBatalhaRequestDTO request) {
                System.out.println("chegou no controll os ids como " + request.getNinjaDesafiadoId() + " - " + request.getNinjaDesafianteId());
                System.out.println("chegou aqui");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batalhaUseCase.iniciarBatalha(request));
    }

    @PostMapping("/{batalhaId}/acoes")
    @Operation(summary = "Executa uma ação em uma batalha")
    public ResponseEntity<BatalhaResponseDTO> executarAcao(
            @PathVariable Long batalhaId,
            @RequestBody AcaoBatalhaRequestDTO request) {
        return ResponseEntity.ok(batalhaUseCase.executarAcao(batalhaId, request));
    }

    @GetMapping("/{batalhaId}")
    @Operation(summary = "Consulta o estado atual de uma batalha")
    public ResponseEntity<BatalhaResponseDTO> consultarBatalha(
            @PathVariable Long batalhaId) {
        return ResponseEntity.ok(batalhaUseCase.consultarBatalha(batalhaId));
    }
}
