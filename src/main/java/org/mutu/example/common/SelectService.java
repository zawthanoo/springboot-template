package org.mutu.example.common;

import io.r2dbc.spi.Row;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

@Repository
public class SelectService {

    @Autowired
    @Qualifier("primaryDatabaseClient")
    private DatabaseClient primaryDatabaseClient;

    private <T> Mono<T> getOne(String sql, Map<String, Object> paramMap, Function<Row, T> rowMapper) {

        return primaryDatabaseClient.sql(sql)
                .bindValues(paramMap)
                .map((readable, metadata) -> {
                    Row row = (Row) readable;
                    return rowMapper.apply(row);
                })
                .one()
                .onErrorMap(NoSuchElementException.class,
                        e -> new EmptyResultDataAccessException("No results found", 1))
                .onErrorMap(IndexOutOfBoundsException.class,
                        e -> new IncorrectResultSizeDataAccessException("Expected one result", 1, 2));
    }

    private <T> Mono<List<T>> getList(String sql, Map<String, Object> paramMap, Function<Row, T> rowMapper) {
        return primaryDatabaseClient.sql(sql)
                .bindValues(paramMap)
                .map((readable, metadata) -> {
                    Row row = (Row) readable;
                    return rowMapper.apply(row);
                })
                .all().collectList();
    }

    public <T> Mono<T> getOne(String sql, Map<String, Object> paramMap, Class<T> mappedClass) {
        return getOne(sql, paramMap, new ReactiveBeanPropertyRowMapper<>(mappedClass));
    }

    public <T> Mono<List<T>> getList(String sql, Map<String, Object> paramMap, Class<T> mappedClass) {
        return getList(sql, paramMap, new ReactiveBeanPropertyRowMapper<>(mappedClass));
    }

    public <T> Mono<T> getValue(String sql, Map<String, Object> paramMap, Class<T> valueType) {
        return primaryDatabaseClient.sql(sql)
                .bindValues(paramMap)
                .map((readable, metadata) -> readable.get(0, valueType))
                .one()
                .onErrorMap(NoSuchElementException.class,
                        e -> new EmptyResultDataAccessException("No results found", 1));
    }
}