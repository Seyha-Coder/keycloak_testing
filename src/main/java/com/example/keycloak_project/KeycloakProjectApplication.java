package com.example.keycloak_project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Claims Service",
				version = "1.0",
				description = "Claims Information"
		),
		security = {
				@SecurityRequirement(name = "oauth")
		}
)
@SecurityScheme(
		name = "oauth",
		type = SecuritySchemeType.OAUTH2,
		flows = @OAuthFlows(
				clientCredentials = @OAuthFlow(
						tokenUrl ="http://localhost:8080/realms/Seyha/protocol/openid-connect/token"
				),
				password = @OAuthFlow(
						tokenUrl ="http://localhost:8080/realms/Seyha/protocol/openid-connect/token"
				)
		),
		in = SecuritySchemeIn.HEADER
)
public class KeycloakProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakProjectApplication.class, args);
	}

}
