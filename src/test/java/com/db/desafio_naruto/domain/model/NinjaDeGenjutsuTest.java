package com.db.desafio_naruto.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NinjaDeGenjutsuTest {

    @Test
    void deveUsarJutsuCorretamente() {
        NinjaDeGenjutsu ninja = new NinjaDeGenjutsu();
        ninja.setNome("Kurenai");

        String resultado = ninja.usarJutsu();

        assertEquals("Kurenai está usando um jutsu de Genjutsu!", resultado);
    }

    @Test
    void deveDesviarCorretamente() {
        NinjaDeGenjutsu ninja = new NinjaDeGenjutsu();
        ninja.setNome("Kurenai");

        String resultado = ninja.desviar();

        assertEquals("Kurenai está desviando do ataque usando sua habilidade em Genjutsu!", resultado);
    }
}