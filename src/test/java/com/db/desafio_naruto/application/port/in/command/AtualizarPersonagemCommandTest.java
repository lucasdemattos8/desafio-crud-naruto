package com.db.desafio_naruto.application.port.in.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

import com.db.desafio_naruto.domain.model.Jutsu;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;

class AtualizarPersonagemCommandTest {

    @Test
    void deveCriarCommandQuandoDadosValidos() {
        assertDoesNotThrow(() -> new AtualizarPersonagemCommand(
            1L,
            "Naruto",
            16,
            "Konoha",
            Arrays.asList(new Jutsu(1L, "Rasengan", 30)),
            100,
            TipoNinja.NINJUTSU
        ));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void deveLancarExcecaoQuandoNomeInvalido(String nome) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            new AtualizarPersonagemCommand(
                1L, nome, 16, "Konoha", 
                Arrays.asList(new Jutsu(1L, "Rasengan", 30)),
                100,
                TipoNinja.NINJUTSU
            )
        );
        assertEquals("Nome é obrigatório", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoIdadeNegativa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            new AtualizarPersonagemCommand(
                1L, "Naruto", -1, "Konoha", 
                Arrays.asList(new Jutsu(1L, "Rasengan", 30)),
                100,
                TipoNinja.NINJUTSU
            )
        );
        assertEquals("Idade deve ser positiva", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoChackraNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            new AtualizarPersonagemCommand(
                1L, "Naruto", 16, "Konoha", 
                Arrays.asList(new Jutsu(1L, "Rasengan", 30)),
                -100,
                TipoNinja.NINJUTSU
            )
        );
        assertEquals("Chakra deve ser positivo", exception.getMessage());
    }
}