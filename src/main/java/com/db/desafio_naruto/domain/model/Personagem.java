package com.db.desafio_naruto.domain.model;

public class Personagem {

    private Long id;
    private String nome;
    private int idade;
    private String aldeia;
    private String[] jutsus;
    private int chackra;

    public Personagem() {
    }

    public Personagem(Long id, String nome, int idade, String aldeia, String[] jutsus, int chackra) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.aldeia = aldeia;
        this.jutsus = jutsus;
        this.chackra = chackra;
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

    public String[] getJutsus() {
        return jutsus;
    }

    public void setJutsus(String[] jutsus) {
        this.jutsus = jutsus;
    }

    public int getChackra() {
        return chackra;
    }

    public void setChackra(int chackra) {
        this.chackra = chackra;
    }
}
