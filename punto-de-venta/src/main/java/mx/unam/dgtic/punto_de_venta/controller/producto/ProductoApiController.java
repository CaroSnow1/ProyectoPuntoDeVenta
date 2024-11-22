package mx.unam.dgtic.punto_de_venta.controller.producto;

import jakarta.validation.Valid;
import mx.unam.dgtic.punto_de_venta.exception.CategoriaNoExisteException;
import mx.unam.dgtic.punto_de_venta.model.entities.dto.ProductoDto;
import mx.unam.dgtic.punto_de_venta.service.producto.ProductoDtoService;
import mx.unam.dgtic.punto_de_venta.util.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/producto", produces =  MediaType.APPLICATION_JSON_VALUE)
public class ProductoApiController {

    @Autowired
    ProductoDtoService productoDtoService;

    // Obtener productos
    @GetMapping(path = "/")
    public ResponseEntity<List<ProductoDto>> getAll() {
        List<ProductoDto> productosDto = productoDtoService.buscarProducto();
        return ResponseEntity.ok(productosDto);
    }

    // Listar productos paginados y filtrados por categoría
    @GetMapping(path = "/paginated")
    public ResponseEntity<PageResponse<ProductoDto>> listarProductos(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "7") int size,
            @RequestParam(name = "categoria", required = false) Long categoriaId) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductoDto> productosDto;

        // Verificar si se pasa el filtro por categoría
        if (categoriaId != null) {
            productosDto = productoDtoService.buscarProductoPorCategoria(categoriaId, pageable);
        } else {
            productosDto = productoDtoService.buscarProducto(pageable);
        }

        // Crear la respuesta de paginación
        PageResponse<ProductoDto> response = new PageResponse<>(
                productosDto.getContent(),
                productosDto.getNumber(),
                productosDto.getSize(),
                productosDto.getTotalElements(),
                productosDto.getTotalPages(),
                productosDto.isFirst(),
                productosDto.isLast(),
                productosDto.hasNext(),
                productosDto.hasPrevious()
        );

        return ResponseEntity.ok(response);
    }

    // Obtener un producto por su ID
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductoDto> getById(@PathVariable String id) {
        Optional<ProductoDto> productoDto = productoDtoService.buscarProductoId(id);
        return productoDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un producto
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteProducto(@PathVariable String id) {
        if (productoDtoService.borrar(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo producto
    @PostMapping(path = "/")
    public ResponseEntity<ProductoDto> createProducto(@RequestBody ProductoDto productoDto) throws URISyntaxException, ParseException, CategoriaNoExisteException {
        ProductoDto productoNuevo = productoDtoService.crear(productoDto);
        URI location = new URI("/api/producto/" + productoNuevo.getCodigo());
        return ResponseEntity.created(location).body(productoNuevo);
    }

    // Actualizar un producto
    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductoDto> updateProducto(
            @PathVariable String id,
            @RequestBody ProductoDto productoDto) throws URISyntaxException, ParseException, CategoriaNoExisteException {
        Optional<ProductoDto> productoExistente = productoDtoService.buscarProductoId(id);

        if (productoExistente.isPresent()) {
            productoDto.setCodigo(id); // Asegurarse de no cambiar el ID
            return ResponseEntity.ok(productoDtoService.actualizar(productoDto));
        } else {
            ProductoDto productoNuevo = productoDtoService.crear(productoDto);
            URI location = new URI("/api/producto/" + productoNuevo.getCodigo());
            return ResponseEntity.created(location).body(productoNuevo);
        }
    }

    // Actualizar parcialmente un producto
    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProductoDto> actualizarParcial(@PathVariable String id,
                                                         @RequestBody ProductoDto productoDto) throws ParseException, CategoriaNoExisteException {
        Optional<ProductoDto> productoToUpdate = productoDtoService.buscarProductoId(id);
        if (productoToUpdate.isPresent()) {
            ProductoDto productoParcial = productoToUpdate.get();
            if (productoDto.getNombre() != null) productoParcial.setNombre(productoDto.getNombre());
            if (productoDto.getCategoria() != null) productoParcial.setCategoria(productoDto.getCategoria());
            if (productoDto.getStock() != null) productoParcial.setStock(productoDto.getStock());
            if (productoDto.getPrecioVenta() != null) productoParcial.setPrecioVenta(productoDto.getPrecioVenta());
            if (productoDto.getCostoCompra() != null) productoParcial.setCostoCompra(productoDto.getCostoCompra());
            productoParcial.setCodigo(id); // Asegurarse de no cambiar el ID

            return ResponseEntity.ok(productoDtoService.actualizar(productoParcial));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Total de productos
    @GetMapping("/count")
    public long contarProductos() {
        return productoDtoService.contarProductos();
    }

    // Total de productos por categoría
    @GetMapping("/count-by-category/{idCategoria}")
    public long contarProductosPorCategoria(@PathVariable Long idCategoria) {
        return productoDtoService.contarProductosPorCategoria(idCategoria);
    }
}
