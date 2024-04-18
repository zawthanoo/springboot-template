package org.mutu.example.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mutu.example.config.exception.BusinessLogicException;
import org.mutu.example.config.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "sql")
@Service
public class EmployeeSqlService {
    @Autowired
    @Qualifier("employeeJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    public <T> Optional<T> selectOne(String sql, Map<String, ?> params, Class<T> clazz) throws DAOException {
        return jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(clazz))
                .stream()
                .findFirst();
    }
    
    public <T> List<T> selectOne(String sql, Class<T> clazz) {
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public <T> Optional<T> select(String sql, Class<T> clazz) throws DAOException {
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Map.of(), BeanPropertyRowMapper.newInstance(clazz)));
    }

    public <T> List<T> select(String sql, Map<String, ?> params, Class<T> clazz) {
        try {
            return jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(clazz));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Map<String, Object> selectAsMap(String sql, Map<String, ?> params) throws DAOException {
        return jdbcTemplate.queryForMap(sql, params);
    }

    /**
     * @param sqlStm - the name of the sql file under /resources/sql folder
     * @return - the sql string contained on the file
     */
    @Cacheable(key = "#root.methodName + #sqlStm")
    public String getSqlStatement(String sqlStm) {
        InputStream resource = null;
        try {
            resource = new ClassPathResource("sql/" + sqlStm).getInputStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new BusinessLogicException(sqlStm + " file not found", e.getMessage());
        }
    }
}