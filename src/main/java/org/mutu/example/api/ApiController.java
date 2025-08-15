package org.mutu.example.api;

import org.mutu.example.dto.User;
import org.mutu.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

/**
 * @author Zaw Than Oo
 * @since 14-06-2025
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
@Log4j2
public class ApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public Mono<User> getUser(@PathVariable(value = "username") String username) {
    	log.debug("Test Message");
        return userService.getUser(username);
    }
}
