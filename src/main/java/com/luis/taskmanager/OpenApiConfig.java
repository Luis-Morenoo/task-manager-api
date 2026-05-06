package com.luis.taskmanager;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final Logger log = LoggerFactory.getLogger(OpenApiConfig.class);

    @Bean
    public OpenAPI taskManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manger API")
                        .description("A REST API for managing tasks build with Spring Boot and MongoDB")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Luis Moreno")
                                .url("https://github.com/Luis-Morenoo")));
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> openSwaggerOnStartup() {
        return event -> {
            String url = "http://localhost:8080/swagger-ui/index.html";
            try {
                // Use the absolute path to cmd.exe instead of relying on PATH
                // This prevents PATH hijacking attacks (SonarCloud java:S4036)
                // This only runs on Windows in local development -
                // it gracefully fails in Docker/Linux environments
                String[] command = {
                        "C:\\Windows\\System32\\cmd.exe", "/c", "start", url
                };
                Runtime.getRuntime().exec(command);
            } catch (Exception e) {
                // Silently fail in non-Windows environments like Docker containers
                log.debug("Could not open browser automatically: {}", e.getMessage());
            }
        };
    }
}
