package org.mutu.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsGlobalConfig implements WebFluxConfigurer {
    @Value("${corsConfig.allow-origin:*}")
    private String allowOrigins;
    @Value("${corsConfig.allow-credentials:true}")
    private boolean allowCredentials;
    @Value("${corsConfig.allow-methods:*}")
    private String allowMethods;
    @Value("${corsConfig.allow-headers:*}")
    private String allowHeaders;
    @Value("${corsConfig.expose-headers:*}")
    private String exposeHeaders;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns(allowOrigins.split(","))
            .allowedMethods(allowMethods.split(","))
            .allowedHeaders(allowHeaders.split(","))
            .allowCredentials(allowCredentials)
            .exposedHeaders(exposeHeaders.split(","));
    }
}