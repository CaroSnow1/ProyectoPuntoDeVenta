package mx.unam.dgtic.punto_de_venta.model.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoVendidoDto {

    private Integer idProductoVendido;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal totalProducto;//Columna generada
    private String estado;
    private String producto;
    private Integer venta;

    public ProductoVendidoDto(String producto, Integer cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }
}
