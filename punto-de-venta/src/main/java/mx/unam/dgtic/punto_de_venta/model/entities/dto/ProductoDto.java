package mx.unam.dgtic.punto_de_venta.model.entities.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoDto {
    @NotBlank(message = "El código no debe estar vacío")
    private String codigo;

    @NotBlank(message = "El nombre no debe estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    @NotNull(message = "El precio de venta no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio de venta debe ser un número válido con hasta 10 dígitos enteros y 2 decimales")
    private BigDecimal precioVenta;

    @NotNull(message = "El costo de compra no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo de compra debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El costo de compra debe ser un número válido con hasta 10 dígitos enteros y 2 decimales")
    private BigDecimal costoCompra;

    @NotNull(message = "Debes proporcionar una categoria")
    @NotBlank(message = "Debes proporcionar una categoria")
    private String categoria;

}
