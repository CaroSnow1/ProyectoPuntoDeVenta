package mx.unam.dgtic.punto_de_venta.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@NamedQuery(
        name = "Venta.sumTotalVentasByFechaVenta",
        query = "SELECT SUM(v.totalVenta) FROM Venta v WHERE v.fechaVenta BETWEEN :startDate AND :endDate"
)
@NamedQuery(
        name = "Venta.findTop10ByOrderByTotalVentaDesc",
        query = "SELECT v FROM Venta v ORDER BY v.totalVenta DESC"
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_venta")
    private Integer idVenta;

    @NotNull(message = "El total de la venta no puede ser nulo")
    //@DecimalMin(value = "0.0", inclusive = false, message = "El total de la venta debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El total de la venta debe ser un número válido con hasta 10 dígitos enteros y 2 decimales")
    @Column(name="total_venta")
    private BigDecimal totalVenta;

    @NotNull(message = "El importe pagado no puede ser nulo")
    //@DecimalMin(value = "0.0", inclusive = false, message = "El importe pagado debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El importe pagado debe ser un número válido con hasta 10 dígitos enteros y 2 decimales")
    @Column(name = "importe_pagado")
    private BigDecimal importePagado;

    @DecimalMin(value = "0.0", inclusive = true, message = "El cambio entregado no puede ser negativo")
    @Digits(integer = 10, fraction = 2, message = "El cambio entregado debe ser un número válido con hasta 10 dígitos enteros y 2 decimales")
    @Column(name = "cambio_entregado")
    private BigDecimal cambioEntregado;

    @NotNull(message = "La hora de venta no puede ser nula")
    @Column(name="hora_venta")
    private LocalTime horaVenta;

    @NotNull(message = "La fecha de venta no puede ser nula")
    @Column(name="fecha_venta")
    private LocalDate fechaVenta;

    @NotNull(message = "El tipo de pago no puede ser nulo")
    @Column(name="tipo_pago")
    private Character tipoPago;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    //FK Cajero
    @ManyToOne(targetEntity = Cajero.class)
    @JoinColumn(name="id_cajero")
    private Cajero cajero;

    public Venta(BigDecimal totalVenta, BigDecimal importePagado, BigDecimal cambioEntregado, LocalTime horaVenta, LocalDate fechaVenta, Character tipoPago) {
        this.totalVenta = totalVenta;
        this.importePagado = importePagado;
        this.cambioEntregado = cambioEntregado;
        this.horaVenta = horaVenta;
        this.fechaVenta = fechaVenta;
        this.tipoPago = tipoPago;
    }

    public Venta(Integer idVenta, BigDecimal totalVenta, BigDecimal importePagado, BigDecimal cambioEntregado, LocalTime horaVenta, LocalDate fechaVenta, Character tipoPago) {
        this.idVenta = idVenta;
        this.totalVenta = totalVenta;
        this.importePagado = importePagado;
        this.cambioEntregado = cambioEntregado;
        this.horaVenta = horaVenta;
        this.fechaVenta = fechaVenta;
        this.tipoPago = tipoPago;
    }

}
