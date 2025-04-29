package com.db.desafio_naruto.infrastructure.adapter.in.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.db.desafio_naruto.application.port.in.dto.batalha.AcaoBatalhaRequestDTO;
import com.db.desafio_naruto.application.port.in.dto.batalha.IniciarBatalhaRequestDTO;
import com.db.desafio_naruto.domain.model.enums.TipoAcao;
import com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository.PersonagemJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BatalhaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonagemJpaRepository personagemJpaRepository;

    private String token;
    private Long ninja1Id;
    private Long ninja2Id;

    @BeforeAll
    void setUp() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/auth"))
            .andExpect(status().isOk())
            .andReturn();
            
        token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        personagemJpaRepository.deleteAll();
        
        ninja1Id = criarPersonagemEObterID(criarNarutoJson());
        ninja2Id = criarPersonagemEObterID(criarSasukeJson());
    }

    @Test
    void deveIniciarBatalhaComSucesso() throws Exception {
        var request = new IniciarBatalhaRequestDTO(ninja1Id, ninja2Id);

        mockMvc.perform(post("/api/v1/batalhas")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.finalizada").value(false))
                .andExpect(jsonPath("$.turnoAtual").value(1))
                .andExpect(jsonPath("$.ninjaDesafiante.nome").value("Naruto Uzumaki"))
                .andExpect(jsonPath("$.ninjaDesafiado.nome").value("Sasuke Uchiha"));
    }

    @Test
    void deveExecutarAcaoJutsuComSucesso() throws Exception {
        Integer batalhaId = iniciarBatalha();
        var request = new AcaoBatalhaRequestDTO(ninja1Id, TipoAcao.USAR_JUTSU, "Rasengan");

        mockMvc.perform(post("/api/v1/batalhas/{id}/acoes", batalhaId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(containsString("Naruto Uzumaki está preparando Rasengan! Sasuke Uchiha pode tentar desviar!")))
                .andExpect(jsonPath("$.finalizada").value(false));
    }

    @Test
    void deveExecutarDesvioComSucesso() throws Exception {
        Integer batalhaId = iniciarBatalhaERegistrarAtaque();
        var request = new AcaoBatalhaRequestDTO(ninja2Id, TipoAcao.DESVIAR, null);

        mockMvc.perform(post("/api/v1/batalhas/{id}/acoes", batalhaId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value(anyOf(
                    containsString("conseguiu desviar"),
                    containsString("não conseguiu desviar")
                )));
    }

    @Test
    void deveLancarExcecaoQuandoBatalhaNaoEncontrada() throws Exception {
        var request = new AcaoBatalhaRequestDTO(ninja1Id, TipoAcao.USAR_JUTSU, "Rasengan");

        mockMvc.perform(post("/api/v1/batalhas/{id}/acoes", 999L)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Batalha não encontrada com id: 999"));
    }

    @Test
    void deveLancarExcecaoQuandoTurnoInvalido() throws Exception {
        Integer batalhaId = iniciarBatalha();
        var request = new AcaoBatalhaRequestDTO(ninja2Id, TipoAcao.USAR_JUTSU, "Chidori");

        mockMvc.perform(post("/api/v1/batalhas/{id}/acoes", batalhaId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Não é turno deste ninja jogar"));
    }

    @Test
    void deveConsultarBatalhaEmAndamento() throws Exception {
        Integer batalhaId = iniciarBatalha();

        mockMvc.perform(get("/api/v1/batalhas/{id}", batalhaId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(batalhaId))
                .andExpect(jsonPath("$.finalizada").value(false))
                .andExpect(jsonPath("$.mensagem").value(containsString("Turno 1 - Vez de Naruto")));
    }

    private Integer iniciarBatalha() throws Exception {
        var request = new IniciarBatalhaRequestDTO(ninja1Id, ninja2Id);
        String response = mockMvc.perform(post("/api/v1/batalhas")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        return JsonPath.parse(response).read("$.id");
    }

    private Integer iniciarBatalhaERegistrarAtaque() throws Exception {
        Integer batalhaId = iniciarBatalha();
        var request = new AcaoBatalhaRequestDTO(ninja1Id, TipoAcao.USAR_JUTSU, "Rasengan");
        
        mockMvc.perform(post("/api/v1/batalhas/{id}/acoes", batalhaId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
                
        return batalhaId;
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
}
