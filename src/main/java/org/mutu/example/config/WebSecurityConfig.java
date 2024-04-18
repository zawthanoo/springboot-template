package org.mutu.example.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Zaw Than Oo
 * @since 28-09-2023
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    
    /*
     * comment-in disable security  
     */
//    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
    	http.csrf(csrf -> csrf.disable());
    	
    	/*for h2 console*/
    	http.headers(headers -> headers.frameOptions(fo -> fo.disable()));
    	
		return http.build();
	}
    

    /*
     * comment-in enable security  
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	/*for h2 console*/
    	http.headers(headers -> headers.frameOptions(fo -> fo.disable()));
    	
    	http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
    	http.csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));
    	http.csrf(csrf -> csrf.disable());
    	
    	http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    	
    	http.exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
            response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"Restricted Content\"");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }));    	
    	
    	http.authorizeHttpRequests(authorize -> authorize
    		.requestMatchers(AntPathRequestMatcher.antMatcher("/view/**")).hasAnyRole(ADMIN, USER)	
    		.requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).hasRole(ADMIN)
    		.requestMatchers(
    				AntPathRequestMatcher.antMatcher("/actuator/**"),
    				AntPathRequestMatcher.antMatcher("/h2-console/**"),
    				AntPathRequestMatcher.antMatcher("/**")).permitAll() // <- base path matcher must be last matcher.
    		.anyRequest().authenticated()
        )
    	.oauth2ResourceServer(oauth2Configurer -> oauth2Configurer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");
            var grantedAuthorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
            return new JwtAuthenticationToken(jwt, grantedAuthorities);
        })));
    	return http.build();
    }
    
    @Value("${corsConfig.allow-origin}")
    private String allowOrigins;
    @Value("${corsConfig.allow-credentials}")
    private boolean allowCredentials;
    @Value("${corsConfig.allow-methods}")
    private String allowMethods;
    @Value("${corsConfig.allow-headers}")
    private String allowHeaders;
    @Value("${corsConfig.expose-headers}")
    private String exposeHeaders;
    
    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
    	final var configuration = new CorsConfiguration();
        List<String> originsList = Stream.of(allowOrigins.split(",", -1)).collect(Collectors.toList());
        configuration.setAllowedOrigins(originsList);
        configuration.setAllowCredentials(allowCredentials);
        List<String> methodList = Stream.of(allowMethods.split(",", -1)).collect(Collectors.toList());
        configuration.setAllowedMethods(methodList);
        List<String> headerList = Stream.of(allowHeaders.split(",", -1)).collect(Collectors.toList());
        configuration.setAllowedHeaders(headerList);
        List<String> exposeHeaderList = Stream.of(exposeHeaders.split(",", -1)).collect(Collectors.toList());
        configuration.setExposedHeaders(exposeHeaderList);

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}