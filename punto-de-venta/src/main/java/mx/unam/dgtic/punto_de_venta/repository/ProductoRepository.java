package mx.unam.dgtic.punto_de_venta.repository;

import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    // Método para buscar productos por categoría
    Page<Producto> findByCategoria_IdCategoria(Long categoriaId, Pageable pageable);
    // Contar productos por categoria
    long countByCategoria_IdCategoria(long idCategoria);
}
