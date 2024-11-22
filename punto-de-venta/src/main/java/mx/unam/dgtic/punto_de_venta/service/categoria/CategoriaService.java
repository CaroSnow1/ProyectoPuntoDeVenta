package mx.unam.dgtic.punto_de_venta.service.categoria;

import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Page<Categoria> buscarCategoria(Pageable pageable);
    List<Categoria> buscarCategoria();
    Optional<Categoria> buscarCategoriaId(Integer id);
    Categoria crear(Categoria categoria);
    Categoria actualizar(Categoria categoria);
    boolean borrar(Integer id);
    Categoria buscarCategoriaPorNombre(String nombre);

}
