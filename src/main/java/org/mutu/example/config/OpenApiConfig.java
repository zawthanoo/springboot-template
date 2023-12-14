package org.mutu.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * @author Zaw Than Oo
 * @since 14-04-2023
 */
@Configuration
@EnableWebMvc
public class OpenApiConfig {

    @Value("${appInfo.name}")
    private String appName;

    @Value("${appInfo.description}")
    private String appDesc;

    @Value("${appInfo.version}")
    private String appVersion;
    
    @Value("${appInfo.license.name}")
    private String licenseName;
    
    @Value("${appInfo.license.url}")
    private String licenseUrl;
    
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(appName)
                .description(appDesc)
                .version(appVersion)
                .license(new License().name(licenseName).url(licenseUrl)));
    }
}
