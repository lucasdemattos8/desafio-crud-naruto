package com.db.desafio_naruto.application;

import org.springframework.stereotype.Service;
import com.db.desafio_naruto.application.port.in.ExecutarJutsuUseCase;
import com.db.desafio_naruto.application.port.out.PersonagemRepository;
import com.db.desafio_naruto.domain.model.NinjaDeGenjutsu;
import com.db.desafio_naruto.domain.model.NinjaDeNinjutsu;
import com.db.desafio_naruto.domain.model.NinjaDeTaijutsu;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.interfaces.Ninja;

@Service
public class ExecutarJutsuService implements ExecutarJutsuUseCase {
    
    private final PersonagemRepository personagemRepository;

    public ExecutarJutsuService(PersonagemRepository personagemRepository) {
        this.personagemRepository = personagemRepository;
    }

    @Override
    public String executarJutsu(Long id, boolean isDesviar) {
        Personagem personagemBase = personagemRepository.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));

        Ninja ninja = switch (personagemBase.getTipoNinja()) {
            case NINJUTSU -> {
                NinjaDeNinjutsu ninjaDeNinjutsu = new NinjaDeNinjutsu();
                copyProperties(personagemBase, ninjaDeNinjutsu);
                yield ninjaDeNinjutsu;
            }
            case GENJUTSU -> {
                NinjaDeGenjutsu ninjaDeGenjutsu = new NinjaDeGenjutsu();
                copyProperties(personagemBase, ninjaDeGenjutsu);
                yield ninjaDeGenjutsu;
            }
            case TAIJUTSU -> {
                NinjaDeTaijutsu ninjaDeTaijutsu = new NinjaDeTaijutsu();
                copyProperties(personagemBase, ninjaDeTaijutsu);
                yield ninjaDeTaijutsu;
            }
            default -> throw new IllegalArgumentException("Tipo de ninja não suportado: " + personagemBase.getTipoNinja());
        };

        return isDesviar ? ninja.desviar() : ninja.usarJutsu();
    }

    private void copyProperties(Personagem source, Personagem target) {
        target.setId(source.getId());
        target.setNome(source.getNome());
        target.setIdade(source.getIdade());
        target.setAldeia(source.getAldeia());
        target.setJutsus(source.getJutsus());
        target.setChakra(source.getChakra());
        target.setTipoNinja(source.getTipoNinja());
    }
}