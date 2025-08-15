package org.mutu.example.common;

import io.r2dbc.spi.Row;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ReactiveRowMapper<T> {
    Mono<T> mapRow(Row row) throws Exception;
}