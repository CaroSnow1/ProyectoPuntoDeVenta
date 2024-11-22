package mx.unam.dgtic.punto_de_venta.service.producto;

import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Page<Producto> buscarProducto(Pageable pageable);
    List<Producto> buscarProducto();
    Optional<Producto> buscarProductoId(String id);
    Producto crear(Producto producto);
    Producto actualizar(Producto producto);
    boolean borrar(String id);
    long contarProductos();
    long contarProductosPorCategoria(Long idCategoria);
    Page<Producto> buscarProductoPorCategoria(Long categoriaId, Pageable pageable);
    void actualizarStockProducto(String productoId, int cantidadVendida);

    void validarStock(Producto producto, int cantidad) throws Exception;

    BigDecimal calcularTotalProducto(Producto producto, int cantidad);
}
