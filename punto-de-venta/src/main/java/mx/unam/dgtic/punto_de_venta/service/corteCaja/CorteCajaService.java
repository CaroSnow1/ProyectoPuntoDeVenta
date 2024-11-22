package mx.unam.dgtic.punto_de_venta.service.corteCaja;

import mx.unam.dgtic.punto_de_venta.model.entities.CorteCaja;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CorteCajaService {
    Page<CorteCaja> buscarCorteCaja(Pageable pageable);
    List<CorteCaja> buscarCorteCaja();
    void guardar(CorteCaja corteCaja);
    void borrar(Integer id);
    CorteCaja buscarCorteCajaId(Integer id);
}
