package mx.unam.dgtic.punto_de_venta.repository;

import mx.unam.dgtic.punto_de_venta.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
