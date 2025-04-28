package com.db.desafio_naruto.application.port.in.dto.personagem;

import java.util.List;

import com.db.desafio_naruto.domain.model.enums.TipoNinja;

public class PersonagemBatalhaDTO {
    
    private Long id;
    private String nome;
    private List<String> jutsus;
    private int pontosDeVida;
    private int chakra;
    private TipoNinja tipoNinja;
    
    public PersonagemBatalhaDTO(Long id, String nome, List<String> jutsus, int pontosDeVida, int chakra,
            TipoNinja tipoNinja) {
        this.id = id;
        this.nome = nome;
        this.jutsus = jutsus;
        this.pontosDeVida = pontosDeVida;
        this.chakra = chakra;
        this.tipoNinja = tipoNinja;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<String> getJutsus() {
        return jutsus;
    }
    public void setJutsus(List<String> jutsus) {
        this.jutsus = jutsus;
    }
    public int getPontosDeVida() {
        return pontosDeVida;
    }
    public void setPontosDeVida(int pontosDeVida) {
        this.pontosDeVida = pontosDeVida;
    }
    public int getChakra() {
        return chakra;
    }
    public void setChakra(int chakra) {
        this.chakra = chakra;
    }
    public TipoNinja getTipoNinja() {
        return tipoNinja;
    }
    public void setTipoNinja(TipoNinja tipoNinja) {
        this.tipoNinja = tipoNinja;
    }

}
