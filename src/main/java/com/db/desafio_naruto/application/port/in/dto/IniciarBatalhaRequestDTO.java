package com.db.desafio_naruto.application.port.in.dto;

public class IniciarBatalhaRequestDTO {

    private Long ninjaDesafianteId;
    private Long ninjaDesafiadoId;

    public IniciarBatalhaRequestDTO() {
    }

    public IniciarBatalhaRequestDTO(Long ninjaDesafianteId, Long ninjaDesafiadoId) {
        this.ninjaDesafianteId = ninjaDesafianteId;
        this.ninjaDesafiadoId = ninjaDesafiadoId;
    }

    public Long getNinjaDesafianteId() {
        return ninjaDesafianteId;
    }

    public void setNinjaDesafianteId(Long ninjaDesafianteId) {
        this.ninjaDesafianteId = ninjaDesafianteId;
    }

    public Long getNinjaDesafiadoId() {
        return ninjaDesafiadoId;
    }

    public void setNinjaDesafiadoId(Long ninjaDesafiadoId) {
        this.ninjaDesafiadoId = ninjaDesafiadoId;
    }
}
