package mx.unam.dgtic.punto_de_venta.controller.categoria;

import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import mx.unam.dgtic.punto_de_venta.service.categoria.CategoriaService;
import mx.unam.dgtic.punto_de_venta.util.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/categoria", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaApiController {

    @Autowired
    CategoriaService categoriaService;

    //obtener categorias
    @GetMapping(path = "/")
    public ResponseEntity<List<Categoria>> getAll(){
        return ResponseEntity.ok(categoriaService.buscarCategoria());
    }

    // Obtener categorías paginadas
    @GetMapping(path = "/paginated")
    public ResponseEntity<PageResponse<Categoria>> listarCategorias(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "7") int size) {

        // Configurar la paginación
        Pageable pageable = PageRequest.of(page, size);

        // Obtener las categorías paginadas desde el servicio
        Page<Categoria> categorias = categoriaService.buscarCategoria(pageable);

        // Crear el objeto PageResponse
        PageResponse<Categoria> response = new PageResponse<>(
                categorias.getContent(),         // Contenido de la página
                categorias.getNumber(),          // Número de página actual
                categorias.getSize(),            // Número de elementos por página
                categorias.getTotalElements(),   // Total de elementos disponibles
                categorias.getTotalPages(),      // Total de páginas
                categorias.isFirst(),            // ¿Es la primera página?
                categorias.isLast(),             // ¿Es la última página?
                categorias.hasNext(),            // ¿Hay siguiente?
                categorias.hasPrevious()        // ¿Hay página anterior?
        );

        // Devolver la respuesta con un código 200 OK
        return ResponseEntity.ok(response);
    }

    //obtener una categoria
    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Categoria>> getById(@PathVariable Integer id){
        Optional<Categoria> categoria = categoriaService.buscarCategoriaId(id);
        if (categoria.isPresent()){
            return ResponseEntity.ok(categoria);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar una categoria
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Boolean> deleteCategoria(@PathVariable Integer id){
        if(categoriaService.borrar(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //alta categoria
    @PostMapping(path="/")
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) throws URISyntaxException {
        Categoria categoriaNueva = categoriaService.crear(categoria);
        URI location = new URI("api/categoria/"+ categoriaNueva.getIdCategoria());
        return ResponseEntity.created(location).body(categoriaNueva);
    }

    //actualizar categoria
    @PutMapping(path="/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Integer id,
                                                     @RequestBody Categoria categoria) throws URISyntaxException {
        Optional<Categoria> categoriaDb = categoriaService.buscarCategoriaId(id);
        if(categoriaDb.isPresent()){
            return ResponseEntity.ok(categoriaService.actualizar(categoria));
        }else{
            Categoria categoriaNueva = categoriaService.crear(categoria);
            URI location = new URI("api/categoria/" + categoriaNueva.getIdCategoria());
            return ResponseEntity.created(location).body(categoriaNueva);
        }
    }

    //Actualizacion parcial de categoria
    @PatchMapping(path = "/{id}")
    public ResponseEntity<Categoria> actualizacionParcial(@PathVariable Integer id,
                                                          @RequestBody Categoria categoria){
        Optional<Categoria> categoriaToUpdate = categoriaService.buscarCategoriaId(id);
        if(categoriaToUpdate.isPresent()){
            Categoria categoriaParcial = categoriaToUpdate.get();

            if(categoria.getNombreCategoria() != null) categoriaParcial.setNombreCategoria(categoria.getNombreCategoria());
            if(categoria.getEstatus() != null) categoriaParcial.setEstatus(categoria.getEstatus());

            return ResponseEntity.ok(categoriaService.actualizar(categoriaParcial));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
