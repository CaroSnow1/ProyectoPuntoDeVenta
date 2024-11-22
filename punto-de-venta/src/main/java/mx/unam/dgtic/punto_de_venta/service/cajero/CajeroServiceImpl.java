package mx.unam.dgtic.punto_de_venta.service.cajero;

import mx.unam.dgtic.punto_de_venta.model.entities.Cajero;
import mx.unam.dgtic.punto_de_venta.repository.CajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CajeroServiceImpl implements CajeroService{

    @Autowired
    CajeroRepository cajeroRepository;

    @Override
    public Page<Cajero> buscarCajero(Pageable pageable) {
        return cajeroRepository.findAll(pageable);
    }

    @Override
    public List<Cajero> buscarCajero() {
        return cajeroRepository.findAll();
    }

    @Override
    public void guardar(Cajero cajero) {
        cajeroRepository.save(cajero);
    }

    @Override
    public void borrar(Integer id) {
        cajeroRepository.deleteById(id);
    }

    @Override
    public Optional<Cajero> buscarCajeroId(Integer id) {
        return cajeroRepository.findById(id);
    }


}
