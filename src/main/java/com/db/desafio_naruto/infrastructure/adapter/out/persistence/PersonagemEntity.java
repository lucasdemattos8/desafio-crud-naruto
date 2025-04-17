package com.db.desafio_naruto.infrastructure.adapter.out.persistence;

import java.util.List;

import com.db.desafio_naruto.domain.model.enums.TipoNinja;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personagens")
public class PersonagemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private int idade;
    private String aldeia;
    
    @ElementCollection
    @CollectionTable(name = "personagem_jutsus")
    private List<String> jutsus;
    
    private int chakra;
    
    @Enumerated(EnumType.STRING)
    private TipoNinja tipoNinja;

    public PersonagemEntity() {
    }

    public PersonagemEntity(Long id, String nome, int idade, String aldeia, List<String> jutsus, int chakra,
            TipoNinja tipoNinja) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.aldeia = aldeia;
        this.jutsus = jutsus;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getAldeia() {
        return aldeia;
    }

    public void setAldeia(String aldeia) {
        this.aldeia = aldeia;
    }

    public List<String> getJutsus() {
        return jutsus;
    }

    public void setJutsus(List<String> jutsus) {
        this.jutsus = jutsus;
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
