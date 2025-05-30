package com.db.desafio_naruto.infrastructure.adapter.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.db.desafio_naruto.application.port.in.dto.personagem.PersonagemDTO;
import com.db.desafio_naruto.domain.model.Jutsu;
import com.db.desafio_naruto.domain.model.NinjaDeGenjutsu;
import com.db.desafio_naruto.domain.model.NinjaDeNinjutsu;
import com.db.desafio_naruto.domain.model.NinjaDeTaijutsu;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.JutsuEntity;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.PersonagemEntity;

class PersonagemPersistenceMapperTest {

    private PersonagemMapper mapper;
    private Personagem personagem;
    private PersonagemEntity entity;

    @BeforeEach
    void setUp() {
        JutsuMapper jutsuMapper = new JutsuMapper();
        mapper = new PersonagemMapper(jutsuMapper);

        JutsuEntity jutsuRasengan = new JutsuEntity("Rasengan", 30);
        List<JutsuEntity> jutsusEntity = Arrays.asList(jutsuRasengan);

        personagem = new NinjaDeNinjutsu();
        personagem.setId(1L);
        personagem.setNome("Naruto");
        personagem.setIdade(16);
        personagem.setAldeia("Konoha");
        personagem.setJutsus(Arrays.asList(new Jutsu(null, "Rasengan", 30)));
        personagem.setChakra(100);
        personagem.setTipoNinja(TipoNinja.NINJUTSU);

        entity = new PersonagemEntity();
        entity.setId(1L);
        entity.setNome("Naruto");
        entity.setIdade(16);
        entity.setAldeia("Konoha");
        entity.setJutsus(jutsusEntity);
        entity.setChakra(100);
        entity.setTipoNinja(TipoNinja.NINJUTSU);
    }

    @Test
    void deveMapearParaEntityComSucesso() {
        PersonagemEntity resultado = mapper.toEntity(personagem);

        assertNotNull(resultado);
        assertEquals(personagem.getId(), resultado.getId());
        assertEquals(personagem.getNome(), resultado.getNome());
        assertEquals(personagem.getIdade(), resultado.getIdade());
        assertEquals(personagem.getAldeia(), resultado.getAldeia());
        assertJutsusEquals(personagem.getJutsus(), resultado.getJutsus());
        assertEquals(personagem.getChakra(), resultado.getChakra());
        assertEquals(personagem.getTipoNinja(), resultado.getTipoNinja());
    }

    @Test
    void deveMapearParaDominioNinjaNinjutsuComSucesso() {
        Personagem resultado = mapper.toDomain(entity);

        assertNotNull(resultado);
        assertTrue(resultado instanceof NinjaDeNinjutsu);
        assertEquals(entity.getId(), resultado.getId());
        assertEquals(entity.getNome(), resultado.getNome());
        assertEquals(entity.getIdade(), resultado.getIdade());
        assertEquals(entity.getAldeia(), resultado.getAldeia());
        assertJutsusEquals(entity.getJutsus(), resultado.getJutsus());
        assertEquals(entity.getChakra(), resultado.getChakra());
        assertEquals(entity.getTipoNinja(), resultado.getTipoNinja());
    }

    @Test
    void deveMapearParaDominioNinjaGenjutsuComSucesso() {
        entity.setTipoNinja(TipoNinja.GENJUTSU);
        Personagem resultado = mapper.toDomain(entity);

        assertNotNull(resultado);
        assertTrue(resultado instanceof NinjaDeGenjutsu);
        assertEquals(entity.getId(), resultado.getId());
        assertEquals(entity.getNome(), resultado.getNome());
        assertEquals(entity.getIdade(), resultado.getIdade());
        assertEquals(entity.getAldeia(), resultado.getAldeia());
        assertJutsusEquals(entity.getJutsus(), resultado.getJutsus());
        assertEquals(entity.getChakra(), resultado.getChakra());
        assertEquals(entity.getTipoNinja(), resultado.getTipoNinja());
    }

    @Test
    void deveMapearParaDominioNinjaTaijutsuComSucesso() {
        entity.setTipoNinja(TipoNinja.TAIJUTSU);
        Personagem resultado = mapper.toDomain(entity);

        assertNotNull(resultado);
        assertTrue(resultado instanceof NinjaDeTaijutsu);
        assertEquals(entity.getId(), resultado.getId());
        assertEquals(entity.getNome(), resultado.getNome());
        assertEquals(entity.getIdade(), resultado.getIdade());
        assertEquals(entity.getAldeia(), resultado.getAldeia());
        assertJutsusEquals(entity.getJutsus(), resultado.getJutsus());
        assertEquals(entity.getChakra(), resultado.getChakra());
        assertEquals(entity.getTipoNinja(), resultado.getTipoNinja());
    }

    @Test
    void deveMapearParaDTOComSucesso() {
        PersonagemDTO resultado = mapper.toDto(personagem);

        assertNotNull(resultado);
        assertEquals(personagem.getId(), resultado.getId());
        assertEquals(personagem.getNome(), resultado.getNome());
        assertEquals(personagem.getIdade(), resultado.getIdade());
        assertEquals(personagem.getAldeia(), resultado.getAldeia());
        assertJutsusEquals(personagem.getJutsus(), resultado.getJutsus());
        assertEquals(personagem.getChakra(), resultado.getChakra());
        assertEquals(personagem.getTipoNinja(), resultado.getTipoNinja());
    }

    @Test
    void deveMapearListaParaDTOsComSucesso() {
        List<Personagem> personagens = Arrays.asList(personagem);

        List<PersonagemDTO> resultados = mapper.toDto(personagens);

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals(personagem.getNome(), resultados.get(0).getNome());
    }

    @Test
    void deveMapearPaginaParaDTOsComSucesso() {
        Page<Personagem> page = new PageImpl<>(Arrays.asList(personagem));

        Page<PersonagemDTO> resultado = mapper.toDto(page);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        assertEquals(personagem.getNome(), resultado.getContent().get(0).getNome());
    }

    @Test
    void deveLancarExcecaoParaTipoNinjaInvalido() {
        entity.setTipoNinja(null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> mapper.toDomain(entity)
        );
        assertEquals("Tipo de Ninja não pode ser nulo", exception.getMessage());
    }

    private void assertJutsusEquals(List<?> expected, List<?> actual) {
        assertEquals(expected.size(), actual.size(), "Jutsu lists should have same size");
        
        for (int i = 0; i < expected.size(); i++) {
            if (expected.get(i) instanceof Jutsu && actual.get(i) instanceof JutsuEntity) {
                Jutsu expectedJutsu = (Jutsu) expected.get(i);
                JutsuEntity actualJutsu = (JutsuEntity) actual.get(i);
                assertEquals(expectedJutsu.getNome(), actualJutsu.getNome(), "Jutsu names should match");
                assertEquals(expectedJutsu.getCustoChakra(), actualJutsu.getCustoChakra(), "Jutsu chakra cost should match");
            } else if (expected.get(i) instanceof Jutsu && actual.get(i) instanceof Jutsu) {
                Jutsu expectedJutsu = (Jutsu) expected.get(i);
                Jutsu actualJutsu = (Jutsu) actual.get(i);
                assertEquals(expectedJutsu.getNome(), actualJutsu.getNome(), "Jutsu names should match");
                assertEquals(expectedJutsu.getCustoChakra(), actualJutsu.getCustoChakra(), "Jutsu chakra cost should match");
            }
        }
    }
}