package mx.unam.dgtic.punto_de_venta.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@NamedQuery(
        name = "Gerente.findByHorarioEntradaBetween",
        query = "SELECT g FROM Gerente g WHERE g.horarioEntrada BETWEEN :startTime AND :endTime"
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Gerente {
    @Id
    @Column(name="id_gerente")
    private Integer idGerente;
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

    public Gerente(Date fechaAlta, Character genero, Time horarioSalida, Time horarioEntrada, String apMaterno, String apPaterno, String nombre) {
        this.fechaAlta = fechaAlta;
        this.genero = genero;
        this.horarioSalida = horarioSalida;
        this.horarioEntrada = horarioEntrada;
        this.apMaterno = apMaterno;
        this.apPaterno = apPaterno;
        this.nombre = nombre;
    }

}
