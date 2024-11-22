package mx.unam.dgtic.punto_de_venta.service.gerente;

import mx.unam.dgtic.punto_de_venta.model.entities.Gerente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GerenteService {
    Page<Gerente> buscarGerente(Pageable pageable);
    List<Gerente> buscarGerente();
    void guardar(Gerente gerente);
    void borrar(Integer id);
    Gerente buscarGerenteId(Integer id);
}
