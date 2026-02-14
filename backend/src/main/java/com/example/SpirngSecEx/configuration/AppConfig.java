package com.example.SpirngSecEx.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

//     @Bean
//     public OpenAPI openAPI() {
//         OpenAPI info = new OpenAPI().addSecurityItem(new SecurityRequirement().
//                         addList("Bearer Authentication"))
//                 .components(new Components().addSecuritySchemes
//                         ("Bearer Authentication", createAPIKeyScheme()))
//                 .info(new Info().title("Spring Security app REST API")
//                         .description("This API is used in Spring Security project")
//                         .version("1.0").contact(new Contact().name("Nikos Kaparos")
//                                 .email("nikoskaparos.second@gmail.com").url("https://nikos-kaparos.github.io/kaparoscv/"))
//                         .license(new License().name("License of API")
//                                 .url("https://swagger.io/license/")));
//         return info;
//     }

        @Bean
        public OpenAPI openAPI() {
        // Ορίζουμε τον Server με HTTPS
        Server productionServer = new Server();
        productionServer.setUrl("/");
        productionServer.setDescription("Production Server");

        // Αν θες να δουλεύει και το localhost, μπορείς να προσθέσεις κι άλλον έναν
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development");

        return new OpenAPI()
                .servers(List.of(productionServer, localServer)) // Εδώ προσθέτεις τη λίστα με τους servers
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Spring Security app REST API")
                        .description("This API is used in Spring Security project")
                        .version("1.0")
                        .contact(new Contact().name("Nikos Kaparos")
                                .email("nikoskaparos.second@gmail.com")
                                .url("https://nikos-kaparos.github.io/kaparoscv/"))
                        .license(new License().name("License of API")
                                .url("https://swagger.io/license/")));
        }
}

