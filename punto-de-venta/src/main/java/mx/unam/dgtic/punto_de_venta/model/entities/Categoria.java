package mx.unam.dgtic.punto_de_venta.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NamedQuery(
        name = "Categoria.findProductsCount",
        query = "SELECT c.nombreCategoria, COUNT(p.codigo) " +
                "FROM Categoria c LEFT JOIN Producto p ON c.idCategoria = p.categoria.idCategoria " +
                "GROUP BY c.idCategoria, c.nombreCategoria"
)


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cat_categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private Integer idCategoria;

    @NotBlank(message = "El nombre de la categoría no debe estar en blanco")
    @Size(min = 3, max = 30, message = "El nombre de la categoría debe tener entre 3 y 30 caracteres")
    @Column(name="nombre_categoria")
    private String nombreCategoria;

    @NotBlank(message = "El estatus no puede ser nulo")
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estatus debe ser 'ACTIVO' o 'INACTIVO'")
    private String estatus;

    public Categoria(String categoria, String estatus) {
        this.nombreCategoria = categoria;
        this.estatus = estatus;
    }

}
