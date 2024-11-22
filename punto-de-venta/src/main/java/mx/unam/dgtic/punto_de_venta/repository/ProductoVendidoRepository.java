package mx.unam.dgtic.punto_de_venta.repository;

import mx.unam.dgtic.punto_de_venta.model.entities.ProductoVendido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoVendidoRepository extends JpaRepository<ProductoVendido, Integer> {
    /* ----- Consultas derivadas ----- */
    List<ProductoVendido> findByVentaIdVenta(Integer idVenta);
    List<ProductoVendido> findByEstado(String estado);
    long countByEstado(String estado);

    /* ----- Consultas nombradas ----- */
    BigDecimal sumTotalProductoByVentaId(@Param("ventaId") Long ventaId);

    // Consulta derivada para obtener productos vendidos por idVenta
    List<ProductoVendido> findByVenta_IdVenta(Integer idVenta);
}
