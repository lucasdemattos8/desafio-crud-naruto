package com.db.desafio_naruto.application.port.in.command;

import com.db.desafio_naruto.domain.model.enums.TipoNinja;
import java.util.List;

public class AtualizarPersonagemCommand {
    
    private Long id;
    private String nome;
    private int idade;
    private String aldeia;
    private List<String> jutsus;
    private int chakra;
    private TipoNinja tipoNinja;

    public AtualizarPersonagemCommand() {
    }

    public AtualizarPersonagemCommand(Long id, String nome, int idade, String aldeia,
        List<String> jutsus, int chakra, TipoNinja tipoNinja) {
        validarCampos();
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

    private void validarCampos() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (idade < 0) {
            throw new IllegalArgumentException("Idade deve ser positiva");
        }
        if (chakra < 0) {
            throw new IllegalArgumentException("Chakra deve ser positivo");
        }
        if (tipoNinja == null) {
            throw new IllegalArgumentException("Tipo de ninja é obrigatório");
        }
    }
}