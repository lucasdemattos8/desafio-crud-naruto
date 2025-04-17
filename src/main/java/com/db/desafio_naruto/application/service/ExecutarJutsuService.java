package com.db.desafio_naruto.application.service;

import org.springframework.stereotype.Service;
import com.db.desafio_naruto.application.port.in.ExecutarJutsuUseCase;
import com.db.desafio_naruto.application.port.out.BuscarPorIdPersonagemPort;
import com.db.desafio_naruto.domain.model.NinjaDeGenjutsu;
import com.db.desafio_naruto.domain.model.NinjaDeNinjutsu;
import com.db.desafio_naruto.domain.model.NinjaDeTaijutsu;
import com.db.desafio_naruto.domain.model.Personagem;
import com.db.desafio_naruto.domain.model.enums.TipoNinja;
import com.db.desafio_naruto.domain.model.interfaces.Ninja;

@Service
public class ExecutarJutsuService implements ExecutarJutsuUseCase {
    
    private final BuscarPorIdPersonagemPort personagemRepositoryPort;

    public ExecutarJutsuService(BuscarPorIdPersonagemPort personagemRepositoryPort) {
        this.personagemRepositoryPort = personagemRepositoryPort;
    }

    @Override
    public String executar(Long id, boolean isDesviar) {
        Personagem personagemBase = personagemRepositoryPort.buscarPorId(id)
            .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));
        
        final TipoNinja tipoNinja = personagemBase.getTipoNinja();
        
        if (tipoNinja == null) {
            throw new IllegalArgumentException("Tipo de ninja não pode ser null");
        }

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