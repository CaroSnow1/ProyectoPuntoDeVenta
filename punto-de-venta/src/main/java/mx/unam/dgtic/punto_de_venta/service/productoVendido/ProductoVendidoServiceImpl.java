package mx.unam.dgtic.punto_de_venta.service.productoVendido;

import jakarta.transaction.Transactional;
import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.model.entities.ProductoVendido;
import mx.unam.dgtic.punto_de_venta.model.entities.Venta;
import mx.unam.dgtic.punto_de_venta.repository.ProductoVendidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoVendidoServiceImpl implements ProductoVendidoService{
    @Autowired
    ProductoVendidoRepository productoVendidoRepository;

    @Override
    public Page<ProductoVendido> buscarProductoVendido(Pageable pageable) {
        return productoVendidoRepository.findAll(pageable);
    }


    @Override
    public List<ProductoVendido> buscarProductoVendido() {
        return productoVendidoRepository.findAll();
    }

    //Metodo para obtener productos vendidos asociados a una venta
    @Override
    public List<ProductoVendido> obtenerPorVenta(Integer idVenta) {
        return productoVendidoRepository.findByVentaIdVenta(idVenta);
    }

    //Método para crear productos vendidos asociados a una venta
    @Override
    public ProductoVendido crearProductoVendido(Venta venta, Producto producto, int cantidad, BigDecimal totalProducto) {
        ProductoVendido productoVendido = new ProductoVendido();
        productoVendido.setVenta(venta);
        productoVendido.setProducto(producto);
        productoVendido.setCantidad(cantidad);
        productoVendido.setPrecioUnitario(producto.getPrecioVenta()); //Hacemos el precio unitario igual al precio unitario en producto
        productoVendido.setEstado("vendido"); //Por default configuramos como estado vendido
        productoVendido.setTotalProducto(totalProducto);

        return productoVendidoRepository.save(productoVendido);
    }

    // Método para guardar un producto vendido asociado a una venta
    @Transactional
    @Override
    public ProductoVendido guardar(ProductoVendido productoVendido) {
        return productoVendidoRepository.save(productoVendido);
    }

    @Override
    public boolean borrar(Integer id) {
        Optional<ProductoVendido> productoVendido = productoVendidoRepository.findById(id);
        if(productoVendido.isPresent()){
            productoVendidoRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Optional<ProductoVendido> buscarProductoVendidoId(Integer id) {
        return productoVendidoRepository.findById(id);
    }

    @Override
    public List<ProductoVendido> getProductosVendidosByVenta(Integer idVenta) {
        return productoVendidoRepository.findByVentaIdVenta(idVenta);
    }
}
