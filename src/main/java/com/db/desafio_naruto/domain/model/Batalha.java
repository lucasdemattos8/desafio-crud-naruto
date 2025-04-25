package com.db.desafio_naruto.domain.model;

public class Batalha {
    private Long id;
    private Personagem ninja1;
    private Personagem ninja2;
    private boolean finalizada;
    private int turnoAtual;
    private Long ninjaAtual;
    private int vidaNinja1 = 100;
    private int vidaNinja2 = 100;

    public Batalha() {
    }

    public Batalha(Long id, Personagem ninja1, Personagem ninja2, boolean finalizada, int turnoAtual, Long ninjaAtual) {
        this.id = id;
        this.ninja1 = ninja1;
        this.ninja2 = ninja2;
        this.finalizada = finalizada;
        this.turnoAtual = turnoAtual;
        this.ninjaAtual = ninjaAtual;
    }

    public Batalha(Long id, Personagem ninja1, Personagem ninja2,
                    boolean finalizada, int turnoAtual, Long ninjaAtual,
                    int vidaNinja1, int vidaNinja2) {
        this.id = id;
        this.ninja1 = ninja1;
        this.ninja2 = ninja2;
        this.finalizada = finalizada;
        this.turnoAtual = turnoAtual;
        this.ninjaAtual = ninjaAtual;
        this.vidaNinja1 = vidaNinja1;
        this.vidaNinja2 = vidaNinja2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Personagem getNinja1() {
        return ninja1;
    }

    public void setNinja1(Personagem ninja1) {
        this.ninja1 = ninja1;
    }

    public Personagem getNinja2() {
        return ninja2;
    }

    public void setNinja2(Personagem ninja2) {
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

    public boolean podeJogar(Long ninjaId) {
        return !finalizada && ninjaAtual.equals(ninjaId);
    }

    public void proximoTurno() {
        turnoAtual++;
        ninjaAtual = (ninjaAtual.equals(ninja1.getId())) ? ninja2.getId() : ninja1.getId();
    }

    public void aplicarDano(Long ninjaId, int dano) {
        if (ninjaId.equals(ninja1.getId())) {
            this.vidaNinja1 = Math.max(0, this.vidaNinja1 - dano);
        } else if (ninjaId.equals(ninja2.getId())) {
            this.vidaNinja2 = Math.max(0, this.vidaNinja2 - dano);
        }
    }

    public int getVidaAtual(Long ninjaId) {
        return ninjaId.equals(ninja1.getId()) ? vidaNinja1 : vidaNinja2;
    }

    public boolean estaVivo(Long ninjaId) {
        return getVidaAtual(ninjaId) > 0;
    }

    public boolean batalhaTerminou() {
        return finalizada || vidaNinja1 <= 0 || vidaNinja2 <= 0;
    }
    
}
