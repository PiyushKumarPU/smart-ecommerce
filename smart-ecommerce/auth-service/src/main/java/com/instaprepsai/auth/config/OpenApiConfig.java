package com.instaprepsai.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class OpenApiConfig {

    private final BuildProperties buildProperties;

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Instapreps Authentication Microservices")
                                .description("Instapreps Authentication Microservices** provides a secure and scalable solution for managing user authentication and authorization in a microservices architecture."
                                )
                                .version(buildProperties.getVersion()));
    }
}
