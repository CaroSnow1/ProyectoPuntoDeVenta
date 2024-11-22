package mx.unam.dgtic.punto_de_venta.repository;

import mx.unam.dgtic.punto_de_venta.model.entities.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GerenteRepository extends JpaRepository<Gerente, Integer> {
}
