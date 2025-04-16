package com.db.desafio_naruto.domain.model;

import com.db.desafio_naruto.domain.model.interfaces.Ninja;

public class NinjaDeTaijutsu extends Personagem implements Ninja {

    @Override
    public String usarJutsu() {
        return String.format("%s está usando um golpe de Taijutsu!", getNome());
    }

    @Override
    public String desviar() {
        return String.format("%s está desviando utilizando sua habilidade em Taijutsu!", getNome());
    }
    
}
