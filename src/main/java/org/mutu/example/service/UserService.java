package org.mutu.example.service;

import org.mutu.example.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.mutu.example.dto.User;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Mono<User> getUser(String username) {
        return userRepository.getUser(username);
    }
}
