package com.db.desafio_naruto.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import com.db.desafio_naruto.domain.model.enums.TipoNinja;

class PersonagemTest {

    @Test
    void deveCriarPersonagemComSucesso() {
        List<Jutsu> jutsus = Arrays.asList(
            new Jutsu(null, "Rasengan", 30),
            new Jutsu(null, "Sage Mode", 60)
        );

        Personagem personagem = new Personagem();
        personagem.setNome("Naruto");
        personagem.setIdade(16);
        personagem.setAldeia("Konoha");
        personagem.setChakra(100);
        personagem.setTipoNinja(TipoNinja.NINJUTSU);
        personagem.setJutsus(jutsus);

        assertEquals("Naruto", personagem.getNome());
        assertEquals(16, personagem.getIdade());
        assertEquals("Konoha", personagem.getAldeia());
        assertEquals("Rasengan", personagem.getJutsus().get(0).getNome());
        assertEquals(30, personagem.getJutsus().get(0).getCustoChakra());
        assertEquals("Sage Mode", personagem.getJutsus().get(1).getNome());
        assertEquals(60, personagem.getJutsus().get(1).getCustoChakra());
        assertEquals(100, personagem.getChakra());
        assertEquals(TipoNinja.NINJUTSU, personagem.getTipoNinja());
    }

    @Test
    void deveCriarPersonagemUsandoConstrutorComSucesso() {
        List<Jutsu> jutsus = Arrays.asList(
            new Jutsu(null, "Rasengan", 30),
            new Jutsu(null, "Kage Bunshin", 20)
        );

        Personagem personagem = new Personagem(
            1L, 
            "Naruto", 
            16, 
            "Konoha", 
            jutsus, 
            100
        );

        assertEquals(1L, personagem.getId());
        assertEquals("Naruto", personagem.getNome());
        assertEquals(16, personagem.getIdade());
        assertEquals("Konoha", personagem.getAldeia());
        assertEquals(2, personagem.getJutsus().size());
        assertEquals("Rasengan", personagem.getJutsus().get(0).getNome());
        assertEquals(30, personagem.getJutsus().get(0).getCustoChakra());
        assertEquals("Kage Bunshin", personagem.getJutsus().get(1).getNome());
        assertEquals(100, personagem.getChakra());
    }
}