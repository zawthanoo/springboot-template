package org.mutu.example.config;


import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint((exchange, ex) -> {
                            exchange.getResponse().getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"Restricted Content\"");
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/view/**").hasAnyRole(ADMIN, USER)
                        .pathMatchers("/api/**").hasRole(ADMIN)
                        .pathMatchers("/actuator/**", "/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(this::convertJwt2)));
        return http.build();
    }


    private Mono<JwtAuthenticationToken> convertJwt2(Jwt jwt) {
//        Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
        Collection<String> roles = jwt.getClaimAsStringList("roles");
        var authorities = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        return Mono.just(new JwtAuthenticationToken(jwt, authorities));
    }
}
