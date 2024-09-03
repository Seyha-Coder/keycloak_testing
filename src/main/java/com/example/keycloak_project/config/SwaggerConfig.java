package com.example.keycloak_project.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    String schemeName = "bearerAuth";
    String bearerFormat = "JWT";
    String scheme = "bearer";

//    @Bean
//    public OpenAPI caseOpenAPI() {
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement()
//                        .addList(schemeName)).components(new Components()
//                        .addSecuritySchemes(
//                                schemeName, new SecurityScheme()
//                                        .name(schemeName)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .bearerFormat(bearerFormat)
//                                        .in(SecurityScheme.In.HEADER)
//                                        .scheme(scheme)
//                        )
//                )
//                .info(new Info()
//                        .title("Case Management Service")
//                        .description("Claim Event Information")
//                        .version("1.0")
//                );
//    }
}
