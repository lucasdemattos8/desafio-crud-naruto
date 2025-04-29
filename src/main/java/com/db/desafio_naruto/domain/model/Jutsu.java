package com.db.desafio_naruto.domain.model;

public class Jutsu {
    private Long id;
    private String nome;
    private int custoChakra;

    public Jutsu() {
    }

    public Jutsu(Long id, String nome, int custoChakra) {
        this.id = id;
        this.nome = nome;
        this.custoChakra = custoChakra;
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

    public int getCustoChakra() {
        return custoChakra;
    }

    public void setCustoChakra(int custoChakra) {
        this.custoChakra = custoChakra;
    }

}
