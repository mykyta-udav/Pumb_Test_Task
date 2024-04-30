package com.example.pumb_test_task.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Animal API",
                version = "1.0",
                description = "API for managing animals and their categories"
        ),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenApiConfig {
}
