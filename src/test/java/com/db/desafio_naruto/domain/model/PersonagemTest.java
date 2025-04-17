package com.db.desafio_naruto.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;

class PersonagemTest {

    @Test
    void deveCriarPersonagemComSucesso() {
        Personagem personagem = new Personagem();
        personagem.setNome("Naruto");
        personagem.setIdade(16);
        personagem.setAldeia("Konoha");
        personagem.setJutsus(Arrays.asList("Rasengan", "Kage Bunshin"));
        personagem.setChakra(100);
        personagem.setTipoNinja(TipoNinja.NINJUTSU);

        assertEquals("Naruto", personagem.getNome());
        assertEquals(16, personagem.getIdade());
        assertEquals("Konoha", personagem.getAldeia());
        assertEquals(Arrays.asList("Rasengan", "Kage Bunshin"), personagem.getJutsus());
        assertEquals(100, personagem.getChakra());
        assertEquals(TipoNinja.NINJUTSU, personagem.getTipoNinja());
    }

    @Test
    void deveCriarPersonagemUsandoConstrutorComSucesso() {
        Personagem personagem = new Personagem(
            1L, 
            "Naruto", 
            16, 
            "Konoha", 
            Arrays.asList("Rasengan", "Kage Bunshin"), 
            100
        );

        assertEquals(1L, personagem.getId());
        assertEquals("Naruto", personagem.getNome());
        assertEquals(16, personagem.getIdade());
        assertEquals("Konoha", personagem.getAldeia());
        assertEquals(Arrays.asList("Rasengan", "Kage Bunshin"), personagem.getJutsus());
        assertEquals(100, personagem.getChakra());
    }
}