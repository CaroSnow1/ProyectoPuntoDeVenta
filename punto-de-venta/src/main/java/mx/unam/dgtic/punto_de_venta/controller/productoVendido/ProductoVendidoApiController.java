package mx.unam.dgtic.punto_de_venta.controller.productoVendido;

import mx.unam.dgtic.punto_de_venta.model.entities.ProductoVendido;
import mx.unam.dgtic.punto_de_venta.service.productoVendido.ProductoVendidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/productoVendido", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductoVendidoApiController {
    @Autowired
    ProductoVendidoService productoVendidoService;

    /**
     * Endpoint para obtener los productos vendidos por una venta específica.
     */
    @GetMapping("/{idVenta}/productos-vendidos")
    public ResponseEntity<List<ProductoVendido>> getProductosVendidos(@PathVariable Integer idVenta) {
        List<ProductoVendido> productosVendidos = productoVendidoService.getProductosVendidosByVenta(idVenta);
        if (productosVendidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productosVendidos);
    }
}
