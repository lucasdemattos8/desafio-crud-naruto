package com.db.desafio_naruto.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NinjaDeTaijutsuTest {

    @Test
    void deveUsarJutsuCorretamente() {
        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu();
        ninja.setNome("Rock Lee");

        String resultado = ninja.usarJutsu();
        final String nomeNinja = ninja.getNome();

        assertEquals(nomeNinja + " está usando um golpe de Taijutsu!", resultado);
    }

    @Test
    void deveDesviarCorretamente() {
        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu();
        ninja.setNome("Kurenai");

        String resultado = ninja.desviar();
        final String nomeNinja = ninja.getNome();

        assertEquals(nomeNinja + " está desviando utilizando sua habilidade em Taijutsu!", resultado);
    }
}