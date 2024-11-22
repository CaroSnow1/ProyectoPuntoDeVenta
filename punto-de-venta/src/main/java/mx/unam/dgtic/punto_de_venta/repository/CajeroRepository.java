package mx.unam.dgtic.punto_de_venta.repository;

import mx.unam.dgtic.punto_de_venta.model.entities.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CajeroRepository extends JpaRepository<Cajero, Integer> {
}
