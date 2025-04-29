package com.db.desafio_naruto.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jutsu")
public class JutsuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(name = "custo_chakra")
    private int custoChakra;

    public JutsuEntity() {}

    public JutsuEntity(String nome, int custoChakra) {
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
