package com.db.desafio_naruto.application.port.out;

import java.net.URI;

public interface UriBuilderPort {
    URI buildUri(String path, Object... params);
}
