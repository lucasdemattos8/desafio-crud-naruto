package com.db.desafio_naruto.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.db.desafio_naruto.application.port.in.BuscarTodosPersonagensUseCase;
import com.db.desafio_naruto.application.port.out.BuscarTodosPersonagensPort;
import com.db.desafio_naruto.application.port.out.LogPort;
import com.db.desafio_naruto.domain.model.Personagem;

@Service
public class BuscarTodosPersonagensService implements BuscarTodosPersonagensUseCase {

    private final BuscarTodosPersonagensPort buscarTodosPersonagensPort;
    private final LogPort logPort;

    public BuscarTodosPersonagensService(
            BuscarTodosPersonagensPort buscarTodosPersonagensPort,
            LogPort logPort) {
        this.buscarTodosPersonagensPort = buscarTodosPersonagensPort;
        this.logPort = logPort;
    }

    @Override
    public Page<Personagem> buscarTodos(Pageable pageable) {
        logPort.info("Iniciando busca paginada de personagens: página {}, tamanho {}", 
            pageable.getPageNumber(), pageable.getPageSize());
        
        try {
            Page<Personagem> resultado = buscarTodosPersonagensPort.buscarTodos(pageable);
            logPort.debug("Busca concluída. Total de elementos: {}, Total de páginas: {}", 
                resultado.getTotalElements(), resultado.getTotalPages());
            return resultado;
        } catch (Exception e) {
            logPort.error("Erro ao buscar personagens", e);
            throw e;
        }
    }
}