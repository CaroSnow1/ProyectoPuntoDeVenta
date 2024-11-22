package mx.unam.dgtic.punto_de_venta.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    @Id
    @Column(name="id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String usuario;
    private String contrasenia;
    private int activo;
    @Column(name="tipo_usuario")
    private String tipoUsuario;

    public Usuario(String usuario, String contrasenia, int activo, String tipoUsuario) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.activo = activo;
        this.tipoUsuario = tipoUsuario;
    }
}
