package mx.unam.dgtic.punto_de_venta.service.producto;

import mx.unam.dgtic.punto_de_venta.exception.CategoriaNoExisteException;
import mx.unam.dgtic.punto_de_venta.model.entities.dto.ProductoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ProductoDtoService {

    // Método para obtener una página de productos (Dto)
    Page<ProductoDto> buscarProducto(Pageable pageable);
    List<ProductoDto> buscarProducto();
    Optional<ProductoDto> buscarProductoId(String id);
    ProductoDto crear(ProductoDto productoDto) throws ParseException, CategoriaNoExisteException;
    ProductoDto actualizar(ProductoDto productoDto) throws ParseException, CategoriaNoExisteException;
    boolean borrar(String id);

    // Método para contar todos los productos
    long contarProductos();

    // Método para contar los productos por categoría
    long contarProductosPorCategoria(Long idCategoria);

    // Método para buscar productos filtrados por categoría (Dto)
    Page<ProductoDto> buscarProductoPorCategoria(Long categoriaId, Pageable pageable);

    // Método para actualizar el stock de un producto
    void actualizarStockProducto(String productoId, int cantidadVendida);
}

