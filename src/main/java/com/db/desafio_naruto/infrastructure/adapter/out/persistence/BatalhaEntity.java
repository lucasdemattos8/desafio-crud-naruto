package com.db.desafio_naruto.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "batalha")
public class BatalhaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ninja1_id")
    private PersonagemEntity ninja1;

    @ManyToOne
    @JoinColumn(name = "ninja2_id")
    private PersonagemEntity ninja2;

    @Column(nullable = false)
    private boolean finalizada;

    @Column(name = "turno_atual")
    private int turnoAtual;

    @Column(name = "ninja_atual_id")
    private Long ninjaAtual;

    @Column(name = "vida_ninja1")
    private int vidaNinja1 = 100;

    @Column(name = "vida_ninja2")
    private int vidaNinja2 = 100;

    @Column(name = "chakra_ninja1")
    private Integer chackraNinja1 = 100;

    @Column(name = "chakra_ninja2")
    private Integer chackraNinja2 = 100;

    @Column(name = "ataque_ninja_id")
    private Long ataqueNinjaId;
    
    @Column(name = "ataque_jutsu")
    private String ataqueJutsu;
    
    @Column(name = "ataque_dano_base")
    private Integer ataqueDanoBase;

    public BatalhaEntity() {
    }

    public BatalhaEntity(Long id, PersonagemEntity ninja1, PersonagemEntity ninja2, boolean finalizada, int turnoAtual,
            Long ninjaAtual, int vidaNinja1, int vidaNinja2, int chackraNinja1, int chackraNinja2) {
        this.id = id;
        this.ninja1 = ninja1;
        this.ninja2 = ninja2;
        this.finalizada = finalizada;
        this.turnoAtual = turnoAtual;
        this.ninjaAtual = ninjaAtual;
        this.vidaNinja1 = vidaNinja1;
        this.vidaNinja2 = vidaNinja2;
        this.chackraNinja1 = chackraNinja1;
        this.chackraNinja2 = chackraNinja2;
    }

    public BatalhaEntity(Long id, PersonagemEntity ninja1, PersonagemEntity ninja2, boolean finalizada, int turnoAtual,
            Long ninjaAtual, int vidaNinja1, int vidaNinja2, int chackraNinja1, int chackraNinja2, Long ataqueNinjaId, String ataqueJutsu,
            Integer ataqueDanoBase) {
        this.id = id;
        this.ninja1 = ninja1;
        this.ninja2 = ninja2;
        this.chackraNinja1 = chackraNinja1;
        this.chackraNinja2 = chackraNinja2;
        this.finalizada = finalizada;
        this.turnoAtual = turnoAtual;
        this.ninjaAtual = ninjaAtual;
        this.vidaNinja1 = vidaNinja1;
        this.vidaNinja2 = vidaNinja2;
        this.ataqueNinjaId = ataqueNinjaId;
        this.ataqueJutsu = ataqueJutsu;
        this.ataqueDanoBase = ataqueDanoBase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonagemEntity getNinja1() {
        return ninja1;
    }

    public void setNinja1(PersonagemEntity ninja1) {
        this.ninja1 = ninja1;
    }

    public PersonagemEntity getNinja2() {
        return ninja2;
    }

    public void setNinja2(PersonagemEntity ninja2) {
        this.ninja2 = ninja2;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public int getTurnoAtual() {
        return turnoAtual;
    }

    public void setTurnoAtual(int turnoAtual) {
        this.turnoAtual = turnoAtual;
    }

    public Long getNinjaAtual() {
        return ninjaAtual;
    }

    public void setNinjaAtual(Long ninjaAtual) {
        this.ninjaAtual = ninjaAtual;
    }

    public int getVidaNinja1() {
        return vidaNinja1;
    }

    public void setVidaNinja1(int vidaNinja1) {
        this.vidaNinja1 = vidaNinja1;
    }

    public int getVidaNinja2() {
        return vidaNinja2;
    }

    public void setVidaNinja2(int vidaNinja2) {
        this.vidaNinja2 = vidaNinja2;
    }
    public Long getAtaqueNinjaId() {
        return ataqueNinjaId;
    }

    public void setAtaqueNinjaId(Long ataqueNinjaId) {
        this.ataqueNinjaId = ataqueNinjaId;
    }

    public String getAtaqueJutsu() {
        return ataqueJutsu;
    }

    public void setAtaqueJutsu(String ataqueJutsu) {
        this.ataqueJutsu = ataqueJutsu;
    }

    public Integer getAtaqueDanoBase() {
        return ataqueDanoBase;
    }

    public void setAtaqueDanoBase(Integer ataqueDanoBase) {
        this.ataqueDanoBase = ataqueDanoBase;
    }

    public int getChackraNinja1() {
        return chackraNinja1;
    }

    public void setChakraNinja1(Integer chakraNinja1) {
        this.chackraNinja1 = chakraNinja1 != null ? chakraNinja1 : 100;
    }

    public int getChackraNinja2() {
        return chackraNinja2;
    }

    public void setChakraNinja2(Integer chakraNinja2) {
        this.chackraNinja2 = chakraNinja2 != null ? chakraNinja2 : 100;
    }

}
