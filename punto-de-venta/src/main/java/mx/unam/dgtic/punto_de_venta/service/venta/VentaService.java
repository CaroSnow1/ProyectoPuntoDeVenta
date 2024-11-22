package mx.unam.dgtic.punto_de_venta.service.venta;

import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.model.entities.ProductoVendido;
import mx.unam.dgtic.punto_de_venta.model.entities.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface VentaService {
    Page<Venta> buscarVenta(Pageable pageable);
    List<Venta> buscarVenta();
    Venta guardarVenta(Venta venta);
    Venta crearVentaDefault();
    void agregarProductoAVenta(Venta venta, Producto producto, int cantidad) throws Exception;
    void finalizarVenta(Venta venta, Character tipoPago, BigDecimal importePagado);
    BigDecimal obtenerTotalVenta(Long idVenta);


    //Registrar venta
    //Venta registrarVenta(int idCajero, char tipoPago, BigDecimal importePagado, List<ProductoVendidoDTO> productosVendidos);

    void borrar(Integer id);
    Venta buscarVentaId(Integer id);

    List<ProductoVendido> getProductosVendidos(Integer idVenta);
}
