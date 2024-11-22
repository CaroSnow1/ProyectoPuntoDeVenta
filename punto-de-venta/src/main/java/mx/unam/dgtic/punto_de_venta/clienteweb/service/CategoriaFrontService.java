package mx.unam.dgtic.punto_de_venta.clienteweb.service;

import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import mx.unam.dgtic.punto_de_venta.util.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CategoriaFrontService {
    private final WebClient webClient;

    @Autowired
    public CategoriaFrontService(WebClient.Builder builder){
        this.webClient = builder
                .baseUrl("http://localhost:8080/api/categoria")
                .build();
    }

    /**
     * Obtener todas las categorias lista
     */
    public List<Categoria> getAll(){
        Mono<List<Categoria>> categoriasMono = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(Categoria.class)
                .collectList();
        List<Categoria> categorias =  categoriasMono.block();
        return categorias;
    }

    /**
     * Obtener todas las categorias paginadas
     * @param page
     * @param size
     * return Page<Categoria>
     */
    public Page<Categoria> obtenerCategorias(int page, int size) {
        // Hacer la llamada a la API para obtener las categorías
        PageResponse<Categoria> pageResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/paginated")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PageResponse<Categoria>>() {})
                .block(); // Bloqueamos para obtener la respuesta sincrónicamente

        // Convertir PageResponse en Page
        List<Categoria> categorias = pageResponse.getContent(); // Obtener las categorías de la respuesta
        Page<Categoria> categoriaPage = new PageImpl<>(categorias, PageRequest.of(page, size), pageResponse.getTotalElements());

        return categoriaPage;
    }

    /**
     * Obtener Categoria por Id
     */
    public Categoria getCategoriaById(Integer id){
        Mono<Categoria> categoriaMono =  webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Categoria.class);
        Categoria categoria = categoriaMono.block();
        return categoria;
    }

    /**
     * Crear una nueva categoría.
     */
    public Categoria createCategoria(Categoria categoria) {
        return webClient.post()
                .uri("/")
                .bodyValue(categoria)
                .retrieve()
                .bodyToMono(Categoria.class)
                .block();
    }

    /**
     * Actualizar completamente una categoría por su ID.
     */
    public Categoria updateCategoria(Categoria categoria) {
        return webClient.put()
                .uri("/{id}", categoria.getIdCategoria())
                .bodyValue(categoria)
                .retrieve()
                .bodyToMono(Categoria.class)
                .block();
    }

    /**
     * Actualización parcial de una categoría.
     */
    public Categoria updateCategoriaParcial(Categoria categoriaParcial) {
        return webClient.patch()
                .uri("/{id}", categoriaParcial.getIdCategoria())
                .bodyValue(categoriaParcial)
                .retrieve()
                .bodyToMono(Categoria.class)
                .block();
    }

    /**
     *  Eliminar Categoria por Id
     */
    public boolean deleteCategoria(Integer id) {
        return Boolean.TRUE.equals(webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .block());
    }

}
