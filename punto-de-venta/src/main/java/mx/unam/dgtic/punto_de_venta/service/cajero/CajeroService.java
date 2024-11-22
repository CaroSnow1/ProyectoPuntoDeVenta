package mx.unam.dgtic.punto_de_venta.service.cajero;

import mx.unam.dgtic.punto_de_venta.model.entities.Cajero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CajeroService {
    Page<Cajero> buscarCajero(Pageable pageable);
    List<Cajero> buscarCajero();
    void guardar(Cajero cajero);
    void borrar(Integer id);
    Optional<Cajero> buscarCajeroId(Integer id);
}
