package com.db.desafio_naruto.domain.model;

import com.db.desafio_naruto.domain.model.interfaces.Ninja;

public class NinjaDeNinjutsu extends Personagem implements Ninja {

    @Override
    public String usarJutsu() {
        return String.format("%s está usando um jutsu de Ninjutsu!", getNome());
    }

    @Override
    public String desviar() {
        return String.format("%s está desviando do ataque utilizando um jutsu de Ninjutsu!", getNome());
    }
    
}
