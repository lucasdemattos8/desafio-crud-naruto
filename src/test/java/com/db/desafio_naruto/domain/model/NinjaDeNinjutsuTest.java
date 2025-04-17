package com.db.desafio_naruto.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NinjaDeNinjutsuTest {

    @Test
    void deveUsarJutsuCorretamente() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu();
        ninja.setNome("Naruto");

        String resultado = ninja.usarJutsu();

        assertEquals("Naruto está usando um jutsu de Ninjutsu!", resultado);
    }

    @Test
    void deveDesviarCorretamente() {
        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu();
        ninja.setNome("Naruto");

        String resultado = ninja.desviar();

        assertEquals("Naruto está desviando do ataque utilizando um jutsu de Ninjutsu!", resultado);
    }
}
