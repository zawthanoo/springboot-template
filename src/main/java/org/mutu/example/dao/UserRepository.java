package org.mutu.example.dao;

import java.util.Map;

import org.mutu.example.common.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.mutu.example.dto.User;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class UserRepository {
    @Autowired
    private SelectService selectService;

    @Autowired
    @Qualifier("primaryDatabaseClient")
    private DatabaseClient primaryDatabaseClient;

    public Mono<User> getUser(String username) {
        String query = "SELECT USERNAME, PASSWORD, LOCKED FROM USERS WHERE USERNAME = :username";
        return selectService.getOne(query, Map.of("username", username), User.class);
    }
}