package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.desafio_naruto.application.port.in.SalvarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.AtualizarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.DeletarPersonagemUseCase;
import com.db.desafio_naruto.application.port.in.ExecutarJutsuUseCase;
import com.db.desafio_naruto.domain.model.Personagem;


@RestController
@RequestMapping("/personagens")
public class PersonagemController {

    private final SalvarPersonagemUseCase createPersonagemUseCase;
    private final ExecutarJutsuUseCase executarJutsuUseCase;
    private final DeletarPersonagemUseCase deletarPersonagemUseCase;
    private final AtualizarPersonagemUseCase atualizarPersonagemUseCase;

    public PersonagemController(
        SalvarPersonagemUseCase createPersonagemUseCase, ExecutarJutsuUseCase executarJutsuUseCase,
        DeletarPersonagemUseCase deletarPersonagemUseCase, AtualizarPersonagemUseCase atualizarPersonagemUseCase) {
        this.createPersonagemUseCase = createPersonagemUseCase;
        this.executarJutsuUseCase = executarJutsuUseCase;
        this.deletarPersonagemUseCase = deletarPersonagemUseCase;
        this.atualizarPersonagemUseCase = atualizarPersonagemUseCase;
    }    

    @PostMapping
    public ResponseEntity<Personagem> createPerson(@RequestBody Personagem personagem) {
        Personagem personagemDominio = createPersonagemUseCase.salvar(personagem);
        return ResponseEntity.status(201).body(personagemDominio);
    }

    @PostMapping("/{id}/jutsu")
    public ResponseEntity<String> executarJutsu(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean desviar) {
        String resultado = executarJutsuUseCase.executar(id, desviar);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personagem> atualizar(@PathVariable Long id, @RequestBody Personagem personagem) {
        return ResponseEntity.ok(atualizarPersonagemUseCase.atualizar(id, personagem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarPersonagemUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

}