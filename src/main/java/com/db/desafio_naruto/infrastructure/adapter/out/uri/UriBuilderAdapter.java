package com.db.desafio_naruto.infrastructure.adapter.out.uri;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.db.desafio_naruto.application.port.out.UriBuilderPort;

@Component
public class UriBuilderAdapter implements UriBuilderPort {
    
    @Override
    public URI buildUri(String path, Object... params) {
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path(path)
            .buildAndExpand(params)
            .toUri();
    }
}
