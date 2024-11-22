package mx.unam.dgtic.punto_de_venta.service.producto;

import jakarta.transaction.Transactional;
import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    ProductoRepository productoRepository;

    @Override
    public Page<Producto> buscarProducto(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public List<Producto> buscarProducto() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarProductoId(String id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto crear(Producto producto) {
        return  productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto){
        return productoRepository.save(producto);
    }

    @Override
    public boolean borrar(String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            productoRepository.deleteById(id);
            return true;
        }else{
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
    public Page<Producto> buscarProductoPorCategoria(Long categoriaId, Pageable pageable) {
        return productoRepository.findByCategoria_IdCategoria(categoriaId, pageable);
    }

    // Método para actualizar el stock de un producto
    @Override
    @Transactional
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

    @Override
    public void validarStock(Producto producto, int cantidad) throws Exception {
        if (producto.getStock() < cantidad) {
            throw new Exception("Stock insuficiente");
        }
    }

    @Override
    public BigDecimal calcularTotalProducto(Producto producto, int cantidad) {
        return producto.getPrecioVenta().multiply(BigDecimal.valueOf(cantidad));
    }
}
