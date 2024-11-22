package mx.unam.dgtic.punto_de_venta.service.categoria;

import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import mx.unam.dgtic.punto_de_venta.repository.CajeroRepository;
import mx.unam.dgtic.punto_de_venta.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public Page<Categoria> buscarCategoria(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public List<Categoria> buscarCategoria() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> buscarCategoriaId(Integer id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria crear(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria actualizar(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    @Override
    public boolean borrar(Integer id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if(categoria.isPresent()){
            categoriaRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Categoria buscarCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombreCategoria(nombre);
    }
}
