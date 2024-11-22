package mx.unam.dgtic.punto_de_venta.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@NamedQuery(
        name = "ProductoVendido.sumTotalProductoByVentaId",
        query = "SELECT SUM(pv.totalProducto) FROM ProductoVendido "+
                "pv WHERE pv.venta.idVenta = :ventaId AND pv.estado = 'vendido'"
)


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="producto_vendido")
public class ProductoVendido {
    @Id
    @Column(name="id_producto_vendido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProductoVendido;

    private Integer cantidad;
    @Column(name="precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name="total_producto", insertable = false, updatable = false)
    private BigDecimal totalProducto;//Columna generada

    private String estado;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //@JsonIgnore
    //FK Producto
    @ManyToOne(targetEntity = Producto.class)
    @JoinColumn(name = "codigo")
    private Producto producto;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //@JsonIgnore
    //FK Venta
    @ManyToOne(targetEntity = Venta.class)
    @JoinColumn(name="id_venta")
    private Venta venta;

    public ProductoVendido(Integer cantidad, BigDecimal precioUnitario) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public ProductoVendido(Integer cantidad, BigDecimal precioUnitario, BigDecimal totalProducto, String estado) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.totalProducto = totalProducto;
        this.estado = estado;
    }

    public ProductoVendido(Integer cantidad, BigDecimal precioUnitario, Producto producto, Venta venta) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.venta = venta;
    }

    public ProductoVendido(Integer idProductoVendido, Integer cantidad, BigDecimal precioUnitario, BigDecimal totalProducto, String estado) {
        this.idProductoVendido = idProductoVendido;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.totalProducto = totalProducto;
        this.estado = estado;
    }


    public void setSubtotal(BigDecimal subtotal) {
    }
}
