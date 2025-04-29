package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository.PersonagemJpaRepository;
import com.jayway.jsonpath.JsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class PersonagemControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonagemJpaRepository personagemJpaRepository;

    private String token;
    
    private Long narutoId;

    @BeforeEach
    void setUp() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/auth"))
            .andExpect(status().isOk())
            .andReturn();
            
        token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        personagemJpaRepository.deleteAll();
        
        narutoId = criarPersonagemEObterID(criarNarutoJson());
        criarPersonagemEObterID(criarSasukeJson());
        criarPersonagemEObterID(criarSakuraJson());
    }

    @Test
    void deveListarPersonagensComSucesso() throws Exception {
        mockMvc.perform(get("/api/v1/personagens")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(3)))
            .andExpect(jsonPath("$.content[0].nome", is("Naruto Uzumaki")))
            .andExpect(jsonPath("$.content[1].nome", is("Sasuke Uchiha")))
            .andExpect(jsonPath("$.content[2].nome", is("Sakura Haruno")));

    }

    @Test
    void deveBuscarPersonagemPorIdComSucesso() throws Exception {
        mockMvc.perform(get("/api/v1/personagens/" + narutoId)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome", is("Naruto Uzumaki")))
            .andExpect(jsonPath("$.idade", is(16)))
            .andExpect(jsonPath("$.aldeia", is("Konoha")))
            .andExpect(jsonPath("$.tipoNinja", is("NINJUTSU")))
            .andExpect(jsonPath("$.chakra", is(100)));
    }

    @Test
    void deveRetornarUnauthorizedSemToken() throws Exception {
        mockMvc.perform(get("/api/v1/personagens"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarNotFoundParaPersonagemInexistente() throws Exception {
        mockMvc.perform(get("/api/v1/personagens/999")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", containsString("não encontrado")));
    }

    @Test
    void deveExecutarJutsuComSucesso() throws Exception {
        mockMvc.perform(post("/api/v1/personagens/" + narutoId + "/jutsu")
                .header("Authorization", "Bearer " + token)
                .param("desviar", "false"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Naruto Uzumaki está usando um jutsu de Ninjutsu!")));
    }

    @Test
    void deveCriarNovoPersonagemComSucesso() throws Exception {
        String personagemJson = """
            {
                "nome": "Hinata Hyuga",
                "idade": 16,
                "aldeia": "Konoha",
                "tipoNinja": "TAIJUTSU",
                "chakra": 75,
                "jutsus": [
                    {
                        "nome": "Byakugan",
                        "custoChakra": 15
                    },
                    {
                        "nome": "Punho Suave",
                        "custoChakra": 25
                    }
                ]
            }
            """;

        mockMvc.perform(post("/api/v1/personagens")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(personagemJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nome", is("Hinata Hyuga")))
            .andExpect(jsonPath("$.idade", is(16)))
            .andExpect(jsonPath("$.aldeia", is("Konoha")))
            .andExpect(jsonPath("$.tipoNinja", is("TAIJUTSU")))
            .andExpect(jsonPath("$.chakra", is(75)))
            .andExpect(jsonPath("$.jutsus[0]", is("Byakugan")))
            .andExpect(jsonPath("$.jutsus[1]", is("Punho Suave")));
    }

    @Test
    void deveAtualizarPersonagemComSucesso() throws Exception {
        String atualizacaoJson = """
            {
                "nome": "Naruto Uzumaki",
                "idade": 17,
                "aldeia": "Konoha",
                "tipoNinja": "NINJUTSU",
                "chakra": 120,
                    "jutsus": [
                    {
                        "nome": "Rasengan",
                        "custoChakra": 15
                    },
                    {
                        "nome": "Kage Bunshin no Jutsu",
                        "custoChakra": 25
                    }
                ]
            }
            """;

        mockMvc.perform(put("/api/v1/personagens/" + narutoId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(atualizacaoJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome", is("Naruto Uzumaki")))
            .andExpect(jsonPath("$.idade", is(17)))
            .andExpect(jsonPath("$.aldeia", is("Konoha")))
            .andExpect(jsonPath("$.tipoNinja", is("NINJUTSU")))
            .andExpect(jsonPath("$.chakra", is(120)))
            .andExpect(jsonPath("$.jutsus[0]", is("Rasengan")))
            .andExpect(jsonPath("$.jutsus[1]", is("Kage Bunshin no Jutsu")));
    }

    @Test
    void deveDeletarPersonagemComSucesso() throws Exception {
        mockMvc.perform(delete("/api/v1/personagens/" + narutoId)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/personagens/" + narutoId)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());
    }

    @Test
    void deveExecutarJutsuComDesvioComSucesso() throws Exception {
        mockMvc.perform(post("/api/v1/personagens/" + narutoId + "/jutsu")
                .header("Authorization", "Bearer " + token)
                .param("desviar", "true"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Naruto Uzumaki está desviando do ataque utilizando um jutsu de Ninjutsu!")));
    }

    @Test
    void devePaginarResultadosCorretamente() throws Exception {
        mockMvc.perform(get("/api/v1/personagens")
                .header("Authorization", "Bearer " + token)
                .param("page", "0")
                .param("size", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.totalElements", is(3)))
            .andExpect(jsonPath("$.totalPages", is(2)));
    }

    @Test
    void deveOrdenarPorNomeComSucesso() throws Exception {
        mockMvc.perform(get("/api/v1/personagens")
                .header("Authorization", "Bearer " + token)
                .param("sort", "nome"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].nome", is("Naruto Uzumaki")))
            .andExpect(jsonPath("$.content[1].nome", is("Sakura Haruno")))
            .andExpect(jsonPath("$.content[2].nome", is("Sasuke Uchiha")));
    }

    @Test
    void deveRetornarTokenInvalidoComHeaderIncorreto() throws Exception {
        mockMvc.perform(get("/api/v1/personagens")
                .header("Authorization", "Bearer " + "token_invalido"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarBadRequestAoEnviarEnumInvalido() throws Exception {
        String personagemJson = """
            {
                "nome": "Kakashi",
                "idade": 27,
                "aldeia": "Konoha",
                "tipoNinja": "INVALID_TYPE",
                "chakra": 90
            }
            """;
    
        mockMvc.perform(post("/api/v1/personagens")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(personagemJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Valores aceitos")));
    }
    
    @Test
    void deveRetornarNotFoundAoBuscarPersonagemInexistente() throws Exception {
        mockMvc.perform(get("/api/v1/personagens/999")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", containsString("não encontrado")));
    }
    
    @Test
    void deveRetornarUnauthorizedQuandoTokenInvalido() throws Exception {
        mockMvc.perform(get("/api/v1/personagens")
                .header("Authorization", "Bearer " + "token_invalido"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message", containsString("Token inválido ou ausente")));
    }
    
    @Test
    void deveRetornarBadRequestAoEnviarJsonInvalido() throws Exception {
        String invalidJson = "{invalid_json}";
    
        mockMvc.perform(post("/api/v1/personagens")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }
    
    @Test
    void deveRetornarBadRequestAoEnviarRequestSemBody() throws Exception {
        mockMvc.perform(post("/api/v1/personagens")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    private Long criarPersonagemEObterID(String personagemJson) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/personagens")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(personagemJson))
                .andExpect(status().isCreated())
                .andReturn();
        
        Integer id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        return id.longValue();
    }

    private String criarNarutoJson() {
        return """
            {
                "nome": "Naruto Uzumaki",
                "idade": 16,
                "aldeia": "Konoha",
                "tipoNinja": "NINJUTSU",
                "chakra": 100,
                "jutsus": [
                    {
                        "nome": "Rasengan",
                        "custoChakra": 30
                    },
                    {
                        "nome": "Kage Bunshin no Jutsu",
                        "custoChakra": 20
                    }
                ]
            }
            """;
    }

    private String criarSasukeJson() {
        return """
            {
                "nome": "Sasuke Uchiha",
                "idade": 16,
                "aldeia": "Konoha",
                "tipoNinja": "NINJUTSU",
                "chakra": 95,
                "jutsus": [
                    {
                        "nome": "Chidori",
                        "custoChakra": 35
                    },
                    {
                        "nome": "Sharingan",
                        "custoChakra": 25
                    }
                ]
            }
            """;
    }
    
    private String criarSakuraJson() {
        return """
            {
                "nome": "Sakura Haruno",
                "idade": 16,
                "aldeia": "Konoha",
                "tipoNinja": "TAIJUTSU",
                "chakra": 70,
                "jutsus": [
                    {
                        "nome": "Chakra no Mesu",
                        "custoChakra": 15
                    },
                    {
                        "nome": "Okasho",
                        "custoChakra": 25
                    }
                ]
            }
            """;
    }
}