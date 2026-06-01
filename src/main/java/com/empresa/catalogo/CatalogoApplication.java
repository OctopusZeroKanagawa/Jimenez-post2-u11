package com.empresa.catalogo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación.
 * @OpenAPIDefinition configura los metadatos de la documentación Swagger UI.
 *
 * @author Andres Felipe Jimenez Ramirez
 * Programación Web - Unidad 11 - Post-Contenido 2
 */
@OpenAPIDefinition(
        info = @Info(
                title = "API Catálogo de Productos",
                version = "1.0",
                description = "API REST para la gestión del catálogo de productos. " +
                        "Implementa principios SOLID, patrones DAO/DTO y manejo centralizado de errores."
        )
)
@SpringBootApplication
public class CatalogoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogoApplication.class, args);
    }
}
