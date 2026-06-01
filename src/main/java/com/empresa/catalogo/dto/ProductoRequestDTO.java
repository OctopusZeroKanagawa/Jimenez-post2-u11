package com.empresa.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * DTO de entrada documentado con anotaciones @Schema de OpenAPI.
 * Cada campo incluye descripción y ejemplo para Swagger UI.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
@Schema(description = "Datos requeridos para crear o actualizar un producto")
public class ProductoRequestDTO {

    @Schema(
            description = "Nombre del producto",
            example = "Laptop HP ProBook",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(
            description = "Precio del producto en pesos colombianos",
            example = "3500000.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;

    @Schema(
            description = "Categoría del producto",
            allowableValues = {"ELECTRONICA", "PAPELERIA", "HOGAR", "ROPA", "ALIMENTOS"},
            example = "ELECTRONICA"
    )
    private String categoria;

    public ProductoRequestDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
