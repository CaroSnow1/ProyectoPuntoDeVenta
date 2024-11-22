package mx.unam.dgtic.punto_de_venta.service.venta;

import mx.unam.dgtic.punto_de_venta.model.entities.Cajero;
import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.model.entities.ProductoVendido;
import mx.unam.dgtic.punto_de_venta.model.entities.Venta;
import mx.unam.dgtic.punto_de_venta.repository.VentaRepository;
import mx.unam.dgtic.punto_de_venta.service.cajero.CajeroService;
import mx.unam.dgtic.punto_de_venta.service.producto.ProductoService;
import mx.unam.dgtic.punto_de_venta.service.productoVendido.ProductoVendidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements VentaService{

    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    ProductoVendidoService productoVendidoService;
    @Autowired
    ProductoService productoService;
    @Autowired
    CajeroService cajeroService;


    @Override
    public Page<Venta> buscarVenta(Pageable pageable) {
        return ventaRepository.findAll(pageable);
    }

    @Override
    public List<Venta> buscarVenta() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

        /*
    Registrar venta:
    1. Crear la venta
    2. Registrar los productos vendidos asosiados a la venta
    3. Calcular los atributos de venta con los productos vendidos
    4. Setear los atributos
    5. Actualizar stock en Productos
    6. Actualizar la venta
     */

    @Override
    public Venta crearVentaDefault() {

        Optional<Cajero> cajero = cajeroService.buscarCajeroId(1);//Por el momento se pone todo al cajero 1

        Venta venta = new Venta();
        //Setear datos not null para crear una nueva venta
        venta.setCambioEntregado(BigDecimal.valueOf(0.0));
        venta.setImportePagado(BigDecimal.valueOf(0.0));
        venta.setTotalVenta(BigDecimal.valueOf(0.0));
        venta.setHoraVenta(LocalTime.now());
        venta.setFechaVenta(LocalDate.now());
        venta.setTipoPago('E');
        venta.setCajero(cajero.get());//Por el momento todas las ventas estarán con el idCajero 1

        return ventaRepository.save(venta);
    }

    @Override
    public void agregarProductoAVenta(Venta venta, Producto producto, int cantidad) throws Exception {
        // Validar stock
        productoService.validarStock(producto, cantidad);

        // Calcular total del producto vendido
        BigDecimal totalProducto = productoService.calcularTotalProducto(producto, cantidad);

        // Crear y guardar el objeto ProductoVendido
        ProductoVendido productoVendido = productoVendidoService.crearProductoVendido(venta, producto, cantidad, totalProducto);

        // Actualizar stock del producto
        productoService.actualizarStockProducto(producto.getCodigo(), cantidad);

        // Actualizar total de la venta
        venta.setTotalVenta(venta.getTotalVenta().add(totalProducto));
        ventaRepository.save(venta);
    }

    @Override
    public void finalizarVenta(Venta venta,Character tipoPago, BigDecimal importePagado) {
        // Verificar si la venta tiene productos asociados
        List<ProductoVendido> productosVendidos = productoVendidoService.obtenerPorVenta(venta.getIdVenta());
        if (productosVendidos.isEmpty()) {
            throw new IllegalArgumentException("La venta no contiene productos. Agrega productos antes de finalizar la venta.");
        }

        // Verificar si el importe pagado es suficiente
        if (importePagado.compareTo(venta.getTotalVenta()) < 0) {
            throw new IllegalArgumentException("El importe pagado es insuficiente para cubrir el total de la venta.");
        }

        // Asignar el importe pagado y calcular el cambio entregado
        venta.setImportePagado(importePagado);
        venta.setCambioEntregado(importePagado.subtract(venta.getTotalVenta()));

        //Asignar el tipoPago
        venta.setTipoPago(tipoPago);

        // Guardar la venta actualizada en el repositorio
        ventaRepository.save(venta);

    }

    @Override
    public BigDecimal obtenerTotalVenta(Long idVenta) {
        return ventaRepository.obtenerTotalVentaPorId(idVenta);
    }

    @Override
    public void borrar(Integer id) {
        ventaRepository.deleteById(id);
    }

    @Override
    public Venta buscarVentaId(Integer id) {
        Optional<Venta> op = ventaRepository.findById(id);
        return op.orElse(null);
    }

    @Override
    public List<ProductoVendido> getProductosVendidos(Integer idVenta) {
        return productoVendidoService.getProductosVendidosByVenta(idVenta);
    }
}
