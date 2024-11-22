package mx.unam.dgtic.punto_de_venta.repository;

import mx.unam.dgtic.punto_de_venta.model.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    /* -----  Consultas derivadas ----- */
    List<Venta> findByTipoPago(char tipoPago);

    List<Venta> findByCajero_Nombre(String nombre);

    List<Venta> findByFechaVenta(Date fechaVenta);

    long countByFechaVenta(Date fechaVenta);

    long countByFechaVentaBetween(Date fechaInicio, Date fechaFin);

    /* ----- Consultad nombradas -----*/
    // Suma total de las ventas realizadas en un periodo
    BigDecimal sumTotalVentasByFechaVenta(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT v.totalVenta FROM Venta v WHERE v.id = :idVenta")
    BigDecimal obtenerTotalVentaPorId(@Param("idVenta") Long idVenta);
}
