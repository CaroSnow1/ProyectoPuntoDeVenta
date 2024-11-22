package mx.unam.dgtic.punto_de_venta.clienteweb.controller;

import mx.unam.dgtic.punto_de_venta.clienteweb.service.VentaFrontService;
import mx.unam.dgtic.punto_de_venta.model.entities.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/venta")
@SessionAttributes("venta")
public class VentaFrontController {

    @Autowired
    private VentaFrontService ventaFrontService;

    @ModelAttribute("venta")
    public Venta iniciarVenta() {
        return ventaFrontService.crearVentaDefault();
    }

    @GetMapping("/registrar-venta")
    public String registrarVenta(@ModelAttribute("venta") Venta venta, Model model) {
        model.addAttribute("productosVendidos", ventaFrontService.getProductosVendidos(venta.getIdVenta()));
        System.out.println("Registrando venta, ID: " + venta.getIdVenta());
        return "venta/registrar-venta";
    }

    @PostMapping("/{id}/agregar-producto")
    public String agregarProducto(
            @PathVariable Integer id,
            @RequestParam String codigoProducto,
            @RequestParam Integer cantidad,
            @ModelAttribute("venta") Venta venta,
            Model model,
            RedirectAttributes redirectAttributes) {
        String resultado = ventaFrontService.agregarProductoAVenta(id, codigoProducto, cantidad);
        redirectAttributes.addFlashAttribute("mensaje", resultado);

        // Obtener el total de la venta de forma sincrónica
        try {
            BigDecimal total = ventaFrontService.obtenerTotalVenta(id);
            System.out.println("Total venta" + total);
            venta.setTotalVenta(total);  // Actualiza el total en el objeto Venta
            //model.addAttribute("totalVenta", total);  // Pasa el total al modelo
            redirectAttributes.addFlashAttribute("totalVenta", total);
        } catch (Exception e) {
            // Manejar el error si la llamada falla
            //model.addAttribute("mensajeError", "No se pudo obtener el total de la venta.");

            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo obtener el total de la venta.");

        }

        //model.addAttribute("productosVendidos", ventaFrontService.getProductosVendidos(venta.getIdVenta()));
        //return "/venta/registrar-venta";
        redirectAttributes.addFlashAttribute("productosVendidos", ventaFrontService.getProductosVendidos(venta.getIdVenta()));
        return "redirect:/venta/registrar-venta";
    }

    @PostMapping("/{id}/finalizar")
    public String finalizarVenta(
            @PathVariable Integer id,
            @RequestParam(required = false) String tipoPago,
            @RequestParam(required = false) BigDecimal importePagado,
            @ModelAttribute("venta") Venta venta,
            Model model,
            RedirectAttributes redirectAttributes,
            SessionStatus status) {

        try{
            // Validaciones aquí
            if (tipoPago == null || tipoPago.isEmpty()) {
                redirectAttributes.addFlashAttribute("totalVenta", venta.getTotalVenta());
                redirectAttributes.addFlashAttribute("mensajeError", "Tipo de pago es requerido.");
                return "redirect:/venta/registrar-venta";  // Redirigir o devolver vista con el error
            }

            if (importePagado == null) {
                redirectAttributes.addFlashAttribute("totalVenta", venta.getTotalVenta());
                redirectAttributes.addFlashAttribute("mensajeError", "El monto recibido es requerido.");
                return "redirect:/venta/registrar-venta";
            }

            // Lógica de la venta
            if (venta == null || ventaFrontService.getProductosVendidos(venta.getIdVenta()).isEmpty()) {
                redirectAttributes.addFlashAttribute("totalVenta", venta.getTotalVenta());
                redirectAttributes.addFlashAttribute("mensajeError", "No hay productos para cobrar.");
                return "redirect:/venta/registrar-venta";
            }
            String resultado = ventaFrontService.finalizarVenta(id, tipoPago, importePagado);
            redirectAttributes.addFlashAttribute("mensajeExito", "Venta finalizada correctamente");
            // Limpia la venta de la sesión
            status.setComplete();
            return "redirect:/venta/registrar-venta";

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("totalVenta", venta.getTotalVenta());
            redirectAttributes.addFlashAttribute("mensajeError", "Error al finalizar la venta" + e.getMessage());
            return "redirect:/venta/registrar-venta";
        }


    }
}
