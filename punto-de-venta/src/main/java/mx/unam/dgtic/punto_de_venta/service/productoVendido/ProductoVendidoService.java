package mx.unam.dgtic.punto_de_venta.service.productoVendido;

import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.model.entities.ProductoVendido;
import mx.unam.dgtic.punto_de_venta.model.entities.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoVendidoService {
    Page<ProductoVendido> buscarProductoVendido(Pageable pageable);
    List<ProductoVendido> buscarProductoVendido();
    List<ProductoVendido> obtenerPorVenta(Integer idVenta);
    ProductoVendido crearProductoVendido(Venta venta, Producto producto, int cantidad, BigDecimal totalProducto);
    ProductoVendido guardar(ProductoVendido productoVendido);
    boolean borrar(Integer id);
    Optional<ProductoVendido> buscarProductoVendidoId(Integer id);

    /* V E N T A */
    //Lista de productos vendidos asociados a una venta
    List<ProductoVendido> getProductosVendidosByVenta(Integer idVenta);
    //Devolver última venta creada asociada al cajero activo

}
