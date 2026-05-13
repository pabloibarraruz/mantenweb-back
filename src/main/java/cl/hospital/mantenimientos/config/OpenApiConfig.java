package cl.hospital.mantenimientos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${app.openapi.server-url:}")
    private String serverUrl;

    @Bean
    public OpenAPI mantenimientosOpenApi() {
        OpenAPI openApi = new OpenAPI()
                .info(new Info()
                        .title("MantenWeb API")
                        .description("Documentacion de la API del sistema de mantenimientos del hospital.")
                        .version("v1")
                        .license(new License().name("Uso interno")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("Pega aqui el JWT obtenido desde /api/auth/login.")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));

        if (serverUrl != null && !serverUrl.isBlank()) {
            openApi.addServersItem(new Server()
                    .url(serverUrl)
                    .description("Servidor configurado"));
        }

        return openApi;
    }
}
