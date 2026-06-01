# jimenez-post2-u11

**Programación Web — Unidad 11: Buenas Prácticas y Patrones de Diseño**  
**Post-Contenido 2 — Logging con SLF4J/Logback y Documentación con Swagger/OpenAPI**  
**Autor:** Andres Felipe Jimenez Ramirez  
**Universidad Francisco de Paula Santander — 2026**

---

## Tecnologías

| Tecnología              | Versión  | Rol                                          |
|-------------------------|----------|----------------------------------------------|
| Java                    | 17       | Lenguaje                                     |
| Spring Boot             | 3.2.5    | Framework principal                          |
| SLF4J + Logback         | incluido | Logging con niveles y rotación de archivos   |
| springdoc-openapi       | 2.3.0    | Generación automática de Swagger UI          |
| Spring Data JPA         | 3.2.5    | Persistencia (DAO)                           |
| H2 Database             | Runtime  | Base de datos en memoria                     |
| Maven                   | 3.9.x    | Gestor de dependencias                       |

---

## Funcionamiento

### SLF4J / Logback
Logger estático en `ProductoServiceImpl` usando `LoggerFactory.getLogger()`. Niveles aplicados según la naturaleza del evento:

- `DEBUG` — consultas y búsquedas (`buscarPorId`, `listarActivos`)
- `INFO` — operaciones exitosas (`crear`, `actualizar`, `eliminar`)
- `WARN` — recurso no encontrado antes de lanzar excepción
- `ERROR` — reservado para excepciones inesperadas en el handler global

Los mensajes usan placeholders `{}` (nunca concatenación de strings).

`logback-spring.xml` configura dos appenders:
- **CONSOLA** — formato `HH:mm:ss LEVEL logger - mensaje`
- **ARCHIVO** — `logs/catalogo.log` con rotación diaria, 30 días de historial

El nivel del paquete `com.empresa.catalogo` es `DEBUG`; el nivel raíz es `INFO`.

### Swagger / OpenAPI
Anotaciones aplicadas en cada capa:

- `@OpenAPIDefinition` en `CatalogoApplication` — título, versión y descripción de la API
- `@Tag` en `ProductoController` — agrupa endpoints bajo "Productos"
- `@Operation` + `@ApiResponses` en cada método del controlador — describe el propósito y los códigos de respuesta posibles (200, 201, 400, 404, 204)
- `@Parameter` en `@PathVariable` — describe el parámetro con ejemplo
- `@Schema` en `ProductoRequestDTO` y `ProductoResponseDTO` — describe cada campo con descripción y ejemplo concreto

---

## Ejecución

```bash
cd jimenez-post2-u11
mvn spring-boot:run
```

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api-docs
- **H2 Console:** http://localhost:8080/h2-console
- **Logs:** `logs/catalogo.log` (se crea al iniciar la app)

---
