package com.luis.taskmanager;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

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
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
            } catch (Exception e) {
                System.out.println("Could not open browser automatically: " + e.getMessage());
            }
        };
    }
}
