package mx.unam.dgtic.punto_de_venta.service.producto;

import mx.unam.dgtic.punto_de_venta.exception.CategoriaNoExisteException;
import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.model.entities.dto.ProductoDto;
import mx.unam.dgtic.punto_de_venta.repository.ProductoRepository;
import mx.unam.dgtic.punto_de_venta.repository.CategoriaRepository;
import mx.unam.dgtic.punto_de_venta.service.producto.ProductoDtoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoDtoServiceImpl implements ProductoDtoService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Método de conversión de Producto a ProductoDto
    private ProductoDto convertToDto(Producto producto) {
        ProductoDto productoDto = modelMapper.map(producto, ProductoDto.class);
        if (producto.getCategoria() != null) {
            productoDto.setCategoria(producto.getCategoria().getNombreCategoria());
        }
        return productoDto;
    }

    // Método de conversión de ProductoDto a Producto
    private Producto convertToEntity(ProductoDto productoDto) throws ParseException, CategoriaNoExisteException {
        Producto producto = modelMapper.map(productoDto, Producto.class);
        if (productoDto.getCategoria() != null && !productoDto.getCategoria().isEmpty()) {
            Categoria categoria = categoriaRepository.findByNombreCategoria(productoDto.getCategoria());
            if (categoria == null) {
                // Lanzar excepción si la categoría no existe
                throw new CategoriaNoExisteException("La categoría no existe!");
            }
            producto.setCategoria(categoria);
        }
        return producto;
    }

    @Override
    public Page<ProductoDto> buscarProducto(Pageable pageable) {
        Page<Producto> productos = productoRepository.findAll(pageable);
        return productos.map(this::convertToDto); // Convertir cada Producto a ProductoDto
    }

    @Override
    public List<ProductoDto> buscarProducto() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoDto> buscarProductoId(String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            ProductoDto productoDto = convertToDto(producto.get());
            return Optional.of(productoDto);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public ProductoDto crear(ProductoDto productoDto) throws ParseException, CategoriaNoExisteException {
        Producto productoGuardado = productoRepository.save(this.convertToEntity(productoDto));
        return convertToDto(productoGuardado); // Convertir el Producto guardado a ProductoDto
    }

    @Override
    public ProductoDto actualizar(ProductoDto productoDto) throws ParseException, CategoriaNoExisteException {
        Producto productoActualizado = productoRepository.save(this.convertToEntity(productoDto));
        return convertToDto(productoActualizado); // Convertir el Producto actualizado a ProductoDto
    }

    @Override
    public boolean borrar(String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            productoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long contarProductos() {
        return productoRepository.count();
    }

    @Override
    public long contarProductosPorCategoria(Long idCategoria) {
        return productoRepository.countByCategoria_IdCategoria(idCategoria);
    }

    @Override
    public Page<ProductoDto> buscarProductoPorCategoria(Long categoriaId, Pageable pageable) {
        Page<Producto> productos = productoRepository.findByCategoria_IdCategoria(categoriaId, pageable);
        return productos.map(this::convertToDto); // Convertir cada Producto a ProductoDto
    }

    @Override
    public void actualizarStockProducto(String productoId, int cantidadVendida) {
        Optional<Producto> optionalProducto = productoRepository.findById(productoId);

        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            int nuevoStock = producto.getStock() - cantidadVendida;
            producto.setStock(nuevoStock);
            productoRepository.save(producto);
        } else {
            throw new IllegalArgumentException("Producto con ID " + productoId + " no encontrado");
        }
    }
}
