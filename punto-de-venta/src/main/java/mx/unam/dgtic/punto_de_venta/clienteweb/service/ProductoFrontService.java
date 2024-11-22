package mx.unam.dgtic.punto_de_venta.clienteweb.service;

import mx.unam.dgtic.punto_de_venta.model.entities.dto.ProductoDto;
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
import java.util.Optional;

@Service
public class ProductoFrontService {
    private final WebClient webClient;

    @Autowired
    public ProductoFrontService(WebClient.Builder builder){
        this.webClient = builder
                .baseUrl("http://localhost:8080/api/producto")
                .build();
    }

    /**
     * Obtener todos los productos
     */
    public List<ProductoDto> getAll(){
        Mono<List<ProductoDto>> productosMono = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(ProductoDto.class)
                .collectList();
        List<ProductoDto> productos = productosMono.block();
        return productos;
    }

    /**
     * Obtener todos los productos paginados
     */
    public Page<ProductoDto> obtenerProductos(int page, int size, Long categoriaId) {
        PageResponse<ProductoDto> pageResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/paginated")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParamIfPresent("categoria", Optional.ofNullable(categoriaId))
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PageResponse<ProductoDto>>() {})
                .block();

        // Convertir PageResponse en Page
        List<ProductoDto> productos = pageResponse.getContent();
        Page<ProductoDto> productoPage = new PageImpl<>(productos, PageRequest.of(page, size), pageResponse.getTotalElements());
        return productoPage;
    }

    /**
     * Obtener producto por código
     */
    public ProductoDto getProductoById(String id){
        Mono<ProductoDto> productoMono = webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(ProductoDto.class);
        ProductoDto producto = productoMono.block();
        return producto;
    }

    /**
     * Crear producto nuevo
     */
    public ProductoDto createProducto(ProductoDto productoDto){
        return webClient.post()
                .uri("/")
                .bodyValue(productoDto)
                .retrieve()
                .bodyToMono(ProductoDto.class)
                .block();
    }

    /**
     *  Actualizar completamente un producto
     */
    public ProductoDto updateProducto(ProductoDto productoDto){
        return webClient.put()
                .uri("/{id}", productoDto.getCodigo())
                .bodyValue(productoDto)
                .retrieve()
                .bodyToMono(ProductoDto.class)
                .block();
    }

    /**
     * Actualizar parcialmente un producto
     */
    public ProductoDto updateProductoParcial(ProductoDto productoParcial){
        return webClient.patch()
                .uri("/{id}", productoParcial.getCodigo())
                .bodyValue(productoParcial)
                .retrieve()
                .bodyToMono(ProductoDto.class)
                .block();
    }

    /**
     * Eliminar un producto por Id
     */
    public boolean deleteProducto(String id){
        return Boolean.TRUE.equals(webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .block());
    }

    // Método para contar el total de productos
    public long contarProductos() {
        return webClient.get()
                .uri("/count")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    // Método para contar productos por categoría
    public long contarProductosPorCategoria(Long idCategoria) {
        return webClient.get()
                .uri("/count-by-category/{idCategoria}", idCategoria)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }
}
