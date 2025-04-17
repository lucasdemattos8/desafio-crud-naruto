package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_naruto.application.port.in.CreatePersonagemUseCase;
import com.db.desafio_naruto.application.port.in.ExecutarJutsuUseCase;
import com.db.desafio_naruto.domain.model.Personagem;


@RestController
@RequestMapping("/personagens")
public class PersonagemController {

    private final CreatePersonagemUseCase createPersonagemUseCase;
    private final ExecutarJutsuUseCase executarJutsuUseCase;

    public PersonagemController(
        CreatePersonagemUseCase createPersonagemUseCase,
        ExecutarJutsuUseCase executarJutsuUseCase) {
        this.createPersonagemUseCase = createPersonagemUseCase;
        this.executarJutsuUseCase = executarJutsuUseCase;
    }    

    @PostMapping
    public ResponseEntity<Personagem> createPerson(@RequestBody Personagem personagem) {
        Personagem personagemDominio = createPersonagemUseCase.criar(personagem);
        return ResponseEntity.status(201).body(personagemDominio);
    }

    @PostMapping("/{id}/jutsu")
    public ResponseEntity<String> executarJutsu(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean desviar) {
        String resultado = executarJutsuUseCase.executarJutsu(id, desviar);
        return ResponseEntity.ok(resultado);
    }
    
}