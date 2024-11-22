package mx.unam.dgtic.punto_de_venta.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@NamedQuery(
        name = "Cajero.findByHorarioEntradaBetween",
        query = "SELECT c FROM Cajero c WHERE c.horarioEntrada BETWEEN :startTime AND :endTime"
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cajero {
    @Id
    @Column(name = "id_cajero")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCajero;
    private String nombre;
    @Column(name="ap_paterno")
    private String apPaterno;
    @Column(name="ap_materno")
    private String apMaterno;
    @Column(name="horario_entrada")
    private Time horarioEntrada;
    @Column(name="horario_salida")
    private Time horarioSalida;
    private Character genero;
    @Column(name="fecha_alta")
    private Date fechaAlta;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    //FK Usuario
    @OneToOne(targetEntity = Usuario.class)
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    //FK Gerente
    @ManyToOne(targetEntity = Gerente.class)
    @JoinColumn(name="id_gerente")
    private Gerente gerente;


    public Cajero(String nombre, String apPaterno, String apMaterno, Time horarioEntrada, Time horarioSalida, Character genero, Date fechaAlta) {
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.genero = genero;
        this.fechaAlta = fechaAlta;
    }

    public Cajero(String nombre, String apPaterno, String apMaterno, Time horarioEntrada, Time horarioSalida, Character genero, Date fechaAlta, Usuario usuario, Gerente gerente) {
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.genero = genero;
        this.fechaAlta = fechaAlta;
        this.usuario = usuario;
        this.gerente = gerente;
    }

    public Cajero(Integer idCajero, String nombre, String apPaterno, String apMaterno, Time horarioEntrada, Time horarioSalida, Character genero, Date fechaAlta) {
        this.idCajero = idCajero;
        this.nombre = nombre;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.genero = genero;
        this.fechaAlta = fechaAlta;
    }


}
