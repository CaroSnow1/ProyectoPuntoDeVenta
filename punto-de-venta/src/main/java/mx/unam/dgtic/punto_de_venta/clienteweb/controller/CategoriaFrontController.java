package mx.unam.dgtic.punto_de_venta.clienteweb.controller;

import jakarta.validation.Valid;
import mx.unam.dgtic.punto_de_venta.clienteweb.service.CategoriaFrontService;
import mx.unam.dgtic.punto_de_venta.converter.MayusculasConverter;
import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import mx.unam.dgtic.punto_de_venta.util.RenderPagina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "categoria")
public class  CategoriaFrontController {

    @Autowired
    CategoriaFrontService categoriaFrontService;


    @GetMapping("alta-categoria")
    public String altaCategoria(Model model) {
        Categoria categoria = new Categoria();
        categoria.setEstatus("ACTIVO");
        model.addAttribute("categoria", categoria);
        model.addAttribute("accion", "Crear nueva Categoria");
        return "categoria/alta-categoria";
    }


    @PostMapping("salvar-categoria")
    public String salvarCategoria(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result,
                                  Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("accion", categoria.getIdCategoria() == null ? "Crear nueva categoría" : "Modificar categoría");
            return "categoria/alta-categoria";
        }

        // Crear o Actualizar
        if (categoria.getIdCategoria() == null) {
            categoriaFrontService.createCategoria(categoria);
            flash.addFlashAttribute("success", "Categoría creada con éxito");
        } else {
            categoriaFrontService.updateCategoria(categoria);
            flash.addFlashAttribute("success", "Categoría actualizada con éxito");
        }

        return "redirect:/categoria/lista-categoria";
    }

    @GetMapping("/modificar-categoria/{id}")
    public String saltoModificar(@PathVariable("id") Integer id, Model model) {
        Categoria categoria = categoriaFrontService.getCategoriaById(id);
        model.addAttribute("categoria", categoria);
        model.addAttribute("accion", "Modificar categoría");
        return "categoria/alta-categoria";

    }

    @GetMapping("/eliminar-categoria/{id}")
    public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash) {
        if (categoriaFrontService.deleteCategoria(id)) {
            flash.addFlashAttribute("success", "Categoría eliminada con éxito");
        } else {
            flash.addFlashAttribute("error", "Error al eliminar la categoría");
        }
        return "redirect:/categoria/lista-categoria";
    }

    @GetMapping("/lista-categoria")
    public String listaCategoria(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        int size = 7; // Número de elementos por página
        Page<Categoria> categoriaEntities = categoriaFrontService.obtenerCategorias(page, size);

        // Crear RenderPagina para gestionar la paginación
        RenderPagina<Categoria> renderPagina = new RenderPagina<>("lista-categoria", categoriaEntities);

        // Añadir los atributos al modelo para que estén disponibles en la vista
        model.addAttribute("categoria", categoriaEntities.getContent()); // Lista de categorías
        model.addAttribute("page", renderPagina); // Paginación
        model.addAttribute("accion", "Lista de categorías"); // Título o acción de la vista

        // Devolver la vista correspondiente
        return "categoria/lista-categoria";
    }


    @InitBinder("categoria")
    public void nombreCategoria(WebDataBinder binder) {
        binder.registerCustomEditor(String.class,
                "nombreCategoria", new MayusculasConverter());
    }

}
