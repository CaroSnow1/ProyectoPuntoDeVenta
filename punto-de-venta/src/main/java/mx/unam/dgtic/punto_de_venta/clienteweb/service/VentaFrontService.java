package mx.unam.dgtic.punto_de_venta.clienteweb.service;

import mx.unam.dgtic.punto_de_venta.model.entities.ProductoVendido;
import mx.unam.dgtic.punto_de_venta.model.entities.Venta;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VentaFrontService {

    private final WebClient webClient;

    @Autowired
    public VentaFrontService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/venta").build();
    }

    public List<Venta> obtenerTodasLasVentas() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(Venta.class)
                .collectList()
                .block();
    }

    public Venta obtenerVentaPorId(Integer id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Venta.class)
                .block();
    }

    public Venta crearVentaDefault() {
        return webClient.post()
                .uri("/crear")
                .retrieve()
                .bodyToMono(Venta.class)
                .block();
    }

    public String agregarProductoAVenta(Integer id, String codigoProducto, Integer cantidad) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}/agregar-producto")
                        .queryParam("codigoProducto", codigoProducto)
                        .queryParam("cantidad", cantidad)
                        .build(id))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String finalizarVenta(Integer id, String tipoPago, BigDecimal importePagado) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path ("/{id}/finalizar")
                        .queryParam("tipoPago", tipoPago)
                        .queryParam("importePagado",importePagado)
                        .build(id))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void eliminarVenta(Integer id) {
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<ProductoVendido> getProductosVendidos(Integer idVenta) {
        return webClient.get()
                .uri("/{idVenta}/productos-vendidos", idVenta)
                .retrieve()
                .bodyToFlux(ProductoVendido.class) // Para recibir una lista
                .collectList()
                .block();
    }

    // Método para obtener el total de la venta
    public BigDecimal obtenerTotalVenta(Integer ventaId) {
        return webClient.get()
                .uri("/{id}/total", ventaId)  // Hacer una solicitud GET al endpoint de la API
                .retrieve()
                .bodyToMono(BigDecimal.class)  // El cuerpo de la respuesta será un BigDecimal
                .block();
    }
}
