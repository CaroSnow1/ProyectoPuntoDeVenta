package mx.unam.dgtic.punto_de_venta.controller.venta;

import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.model.entities.ProductoVendido;
import mx.unam.dgtic.punto_de_venta.model.entities.Venta;
import mx.unam.dgtic.punto_de_venta.service.producto.ProductoService;
import mx.unam.dgtic.punto_de_venta.service.venta.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venta")
public class VentaApiController {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private ProductoService productoService;

    @GetMapping(path="/")
    public ResponseEntity<List<Venta>> obtenerTodasLasVentas() {
        List<Venta> ventas = ventaService.buscarVenta();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Integer id) {
        Venta venta = ventaService.buscarVentaId(id);
        if (venta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(venta);
    }

    @PostMapping("/crear")
    public ResponseEntity<Venta> crearVentaDefault() {
        Venta nuevaVenta = ventaService.crearVentaDefault();
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    @PostMapping("/{id}/agregar-producto")
    public ResponseEntity<String> agregarProductoAVenta(
            @PathVariable Integer id,
            @RequestParam String codigoProducto,
            @RequestParam Integer cantidad) {
        try {
            //Buscar la venta con el Id proporcionado
            Venta venta = ventaService.buscarVentaId(id);
            if (venta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada");
            }

            Optional<Producto> producto = productoService.buscarProductoId(codigoProducto);
            if (producto == null) throw new Exception("Producto no encontrado");

            ventaService.agregarProductoAVenta(venta, producto.orElseThrow(), cantidad);
            return ResponseEntity.ok("Producto agregado a la venta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<String> finalizarVenta(
            @PathVariable Integer id,
            @RequestParam String tipoPago,
            @RequestParam BigDecimal importePagado) {
        try {
            // Convertir el tipoPago de String a Character
            Character tipoPagoChar = tipoPago.charAt(0);  // Obtienes el primer carácter de la cadena

            Venta venta = ventaService.buscarVentaId(id);
            if (venta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada");
            }

            ventaService.finalizarVenta(venta, tipoPagoChar, importePagado);
            return ResponseEntity.ok("Venta finalizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Integer id) {
        ventaService.borrar(id);
        return ResponseEntity.ok("Venta eliminada correctamente");
    }

    /**
     * Endpoint para obtener los productos vendidos por una venta específica.
     */
    @GetMapping("/{idVenta}/productos-vendidos")
    public ResponseEntity<List<ProductoVendido>> getProductosVendidos(@PathVariable Integer idVenta) {
        List<ProductoVendido> productosVendidos = ventaService.getProductosVendidos(idVenta);
        if (productosVendidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productosVendidos);
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<BigDecimal> obtenerTotalVenta(@PathVariable Integer id) {
        Venta venta = ventaService.buscarVentaId(id);

        if (venta == null) {
            // Si no se encuentra la venta, devolver un 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        BigDecimal total = venta.getTotalVenta();
        return ResponseEntity.ok(total);  // Retornar el total con un 200 OK
    }

}
