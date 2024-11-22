package mx.unam.dgtic.punto_de_venta.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@NamedQuery(
        name = "CorteCaja.sumTotalVentasByFechaCorteBetween",
        query = "SELECT SUM(cc.totalVentas) FROM CorteCaja cc WHERE cc.fechaCorte BETWEEN :startDate AND :endDate"
)
@NamedQuery(
        name = "CorteCaja.findByImporteEntregadoLessThanTotalVentas",
        query = "SELECT cc FROM CorteCaja cc WHERE cc.importeEntregado < cc.totalVentas"
)

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="corte_caja")
public class CorteCaja {
    @Id
    @Column(name="id_corte_caja")
    private Integer idCorteCaja;
    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;
    @Column(name="total_ventas")
    private BigDecimal totalVentas;
    @Column(name="importe_entregado")
    private BigDecimal importeEntregado;
    @Column(name="efectivo_remanente")
    private BigDecimal efectivoRemanente;
    @Column(name="fecha_corte")
    private Date fechaCorte;
    @Column(name="hora_corte")
    private Time horaCorte;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    //FK Cajero
    @ManyToOne(targetEntity = Cajero.class)
    @JoinColumn(name="id_cajero")
    private Cajero cajero;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    //FK Gerente
    @ManyToOne(targetEntity = Gerente.class)
    @JoinColumn(name="id_gerente")
    private Gerente gerente;

    public CorteCaja(BigDecimal saldoInicial, BigDecimal totalVentas, BigDecimal importeEntregado, BigDecimal efectivoRemanente, Date fechaCorte, Time horaCorte) {
        this.saldoInicial = saldoInicial;
        this.totalVentas = totalVentas;
        this.importeEntregado = importeEntregado;
        this.efectivoRemanente = efectivoRemanente;
        this.fechaCorte = fechaCorte;
        this.horaCorte = horaCorte;
    }

    public CorteCaja(Integer idCorteCaja, BigDecimal totalVentas, BigDecimal importeEntregado, BigDecimal efectivoRemanente, Date fechaCorte, Time horaCorte) {
        this.idCorteCaja = idCorteCaja;
        this.totalVentas = totalVentas;
        this.importeEntregado = importeEntregado;
        this.efectivoRemanente = efectivoRemanente;
        this.fechaCorte = fechaCorte;
        this.horaCorte = horaCorte;
    }

    public CorteCaja(BigDecimal saldoInicial, BigDecimal totalVentas, BigDecimal importeEntregado, BigDecimal efectivoRemanente, Date fechaCorte, Time horaCorte, Cajero cajero, Gerente gerente) {
        this.saldoInicial = saldoInicial;
        this.totalVentas = totalVentas;
        this.importeEntregado = importeEntregado;
        this.efectivoRemanente = efectivoRemanente;
        this.fechaCorte = fechaCorte;
        this.horaCorte = horaCorte;
        this.cajero = cajero;
        this.gerente = gerente;
    }


}
