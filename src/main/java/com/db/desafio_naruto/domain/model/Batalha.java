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
    private int chackraNinja1;
    private int chackraNinja2;
    private AtaquePendente ataquePendente;

    public Batalha() {
    }

    public Batalha(Long id, Personagem ninja1, Personagem ninja2, boolean finalizada, int turnoAtual, Long ninjaAtual) {
        this.id = id;
        this.ninja1 = ninja1;
        this.ninja2 = ninja2;
        this.finalizada = finalizada;
        this.turnoAtual = turnoAtual;
        this.ninjaAtual = ninjaAtual;
        this.chackraNinja1 = ninja1.getChakra();
        this.chackraNinja2 = ninja2.getChakra();
        this.ataquePendente = null;
    }

    public Batalha(Long id, Personagem ninja1, Personagem ninja2,
                    boolean finalizada, int turnoAtual, Long ninjaAtual,
                    int vidaNinja1, int vidaNinja2, int chackraNinja1, int chackraNinja2) {
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
        this.ataquePendente = null;
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

    public int getChackraNinja1() {
        return chackraNinja1;
    }

    public void setChackraNinja1(int chackraNinja1) {
        this.chackraNinja1 = chackraNinja1;
        if (this.ninja1 != null) {
            this.ninja1.setChakra(chackraNinja1);
        }
    }

    public int getChackraNinja2() {
        return chackraNinja2;
    }

    public void setChackraNinja2(int chackraNinja2) {
        this.chackraNinja2 = chackraNinja2;
        if (this.ninja2 != null) {
            this.ninja2.setChakra(chackraNinja2);
        }
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

    public void registrarAtaquePendente(Long ninjaId, String nomeJutsu, int danoBase) {
        this.ataquePendente = new AtaquePendente(ninjaId, nomeJutsu, danoBase);
    }
    
    public void limparAtaquePendente() {
        this.ataquePendente = null;
    }

    public boolean temAtaquePendente() {
        return ataquePendente != null;
    }

    public AtaquePendente getAtaquePendente() {
        return ataquePendente;
    }
    public record AtaquePendente(
        Long ninjaAtacanteId,
        String nomeJutsu,
        int danoBase
    ) {}
    
}
