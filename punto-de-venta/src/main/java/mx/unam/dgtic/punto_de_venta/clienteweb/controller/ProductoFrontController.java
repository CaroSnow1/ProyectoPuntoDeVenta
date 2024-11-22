package mx.unam.dgtic.punto_de_venta.clienteweb.controller;

import mx.unam.dgtic.punto_de_venta.clienteweb.service.CategoriaFrontService;
import mx.unam.dgtic.punto_de_venta.clienteweb.service.ProductoFrontService;
import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.model.entities.dto.ProductoDto;
import mx.unam.dgtic.punto_de_venta.util.RenderPagina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoFrontController {

    @Autowired
    private ProductoFrontService productoFrontService;

    @Autowired
    private CategoriaFrontService categoriaFrontService;

    /**
     * Lista de productos con paginación y filtrado
     */
    @GetMapping("/lista-producto")
    public String listaProducto(@RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "categoria", required = false) Long categoriaId,
                                Model model) {
        // Definir el tamaño de la página
        int size = 7;

        // Obtener productos paginados, considerando la categoría si está presente
        Page<ProductoDto> productoDtos = productoFrontService.obtenerProductos(page, size, categoriaId);

        // Generar el renderizado de la página
        RenderPagina<ProductoDto> renderPagina = new RenderPagina<>("lista-producto", productoDtos);

        // Obtener el total de productos
        long totalProductos = (categoriaId != null)
                ? productoFrontService.contarProductosPorCategoria(categoriaId)
                : productoFrontService.contarProductos();

        // Agregar atributos al modelo
        model.addAttribute("producto", productoDtos);
        model.addAttribute("page", renderPagina);
        model.addAttribute("totalProductos", totalProductos);

        // Cargar categorías para el filtro
        List<Categoria> categorias = categoriaFrontService.getAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriaSeleccionada", categoriaId);

        return "/producto/lista-producto";
    }

    /**
     * Crear un nuevo producto
     */
    @GetMapping("/alta-producto")
    public String altaProducto(Model model) {
        ProductoDto productoDto = new ProductoDto();
        model.addAttribute("producto", productoDto);
        System.out.println("producto en alta producto " + productoDto);
        // Cargar categorías para el select
        List<Categoria> categorias = categoriaFrontService.getAll();
        model.addAttribute("categorias", categorias);

        model.addAttribute("accion", "Crear nuevo Producto");
        return "producto/alta-producto";
    }

    /**
     * Guardar producto (Crear o Editar)
     */
    @PostMapping("/salvar-producto")
    public String salvarProducto(@ModelAttribute("producto") ProductoDto productoDto, BindingResult result,
                                 Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("accion", productoDto.getCodigo() == null ? "Crear nuevo producto" : "Modificar producto");
            System.out.println(productoDto.getCodigo() == null ? "Crear nuevo producto" : "Modificar producto");
            return "producto/alta-producto";
        }

        // Validar que la categoría no sea nula
        if (productoDto.getCategoria() == null || productoDto.getCategoria().isEmpty()) {
            flash.addFlashAttribute("error", "La categoría es obligatoria.");
            return "redirect:/producto/alta-producto"; // Volver al formulario
        }

        List<Categoria> categorias = categoriaFrontService.getAll();
        model.addAttribute("categorias", categorias);

        //Crear o actualizar
        if (productoDto.getCodigo() != null && !productoDto.getCodigo().isEmpty()) {
            System.out.println("Producto recibido"+ productoDto);
            productoFrontService.updateProducto(productoDto);

            flash.addFlashAttribute("success", "Producto actualizado con éxito");
        } else {
            productoFrontService.createProducto(productoDto);
            flash.addFlashAttribute("success", "Producto creado con éxito");
        }
        return "redirect:/producto/lista-producto";
    }

    /**
     * Modificar un producto existente
     */
    @GetMapping("/modificar-producto/{id}")
    public String modificarProducto(@PathVariable("id") String id, Model model) {
        ProductoDto productoDto = productoFrontService.getProductoById(id);
        System.out.println("Producto a modificar"+ productoDto);
        model.addAttribute("producto", productoDto);

        // Cargar categorías para el select
        List<Categoria> categorias = categoriaFrontService.getAll();
        model.addAttribute("categorias", categorias);

        model.addAttribute("accion", "Modificar producto");
        return "producto/alta-producto";
    }

    /**
     * Eliminar un producto
     */
    @GetMapping("/eliminar-producto/{id}")
    public String eliminarProducto(@PathVariable("id") String id, RedirectAttributes flash) {
        if (productoFrontService.deleteProducto(id)) {
            flash.addFlashAttribute("success", "Producto eliminado con éxito");
        } else {
            flash.addFlashAttribute("error", "Error al eliminar el producto");
        }
        return "redirect:/producto/lista-producto";
    }
}

/*package mx.unam.dgtic.punto_de_venta.clienteweb.controller;

import mx.unam.dgtic.punto_de_venta.clienteweb.service.CategoriaFrontService;
import mx.unam.dgtic.punto_de_venta.clienteweb.service.ProductoFrontService;
import mx.unam.dgtic.punto_de_venta.model.entities.Categoria;
import mx.unam.dgtic.punto_de_venta.model.entities.Producto;
import mx.unam.dgtic.punto_de_venta.util.RenderPagina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoFrontController {

    @Autowired
    private ProductoFrontService productoFrontService;

    @Autowired
    private CategoriaFrontService categoriaFrontService;

    /**
     * Lista de productos con paginación y filtrado
     */
    /*@GetMapping("/lista-producto")
    public String listaProducto(@RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "categoria", required = false) Long categoriaId,
                                Model model) {
        // Definir el tamaño de la página
        int size = 7;

        // Obtener productos paginados, considerando la categoría si está presente
        Page<Producto> productoEntities = productoFrontService.obtenerProductos(page, size, categoriaId);

        // Generar el renderizado de la página
        RenderPagina<Producto> renderPagina = new RenderPagina<>("lista-producto", productoEntities);

        // Obtener el total de productos
        long totalProductos = (categoriaId != null)
                ? productoFrontService.contarProductosPorCategoria(categoriaId)
                : productoFrontService.contarProductos();

        // Agregar atributos al modelo
        model.addAttribute("producto", productoEntities);
        model.addAttribute("page", renderPagina);
        model.addAttribute("totalProductos", totalProductos);

        // Cargar categorías para el filtro
        List<Categoria> categorias = categoriaFrontService.getAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriaSeleccionada", categoriaId);

        return "producto/lista-producto";
    }

    /**
     * Crear un nuevo producto
     */
    /*@GetMapping("/alta-producto")
    public String altaProducto(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        // Cargar categorías para el select
        List<Categoria> categorias = categoriaFrontService.getAll();
        model.addAttribute("categorias", categorias);

        model.addAttribute("accion", "Crear nuevo Producto");
        return "producto/alta-producto";
    }

    /**
     * Guardar producto (Crear o Editar)
     */
    /*@PostMapping("/salvar-producto")
    public String salvarProducto(@ModelAttribute("producto") Producto producto, BindingResult result,
                                 Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("accion", producto.getCodigo() == null ? "Crear nuevo producto" : "Modificar producto");
            return "producto/alta-producto";
        }

        //Crear o actualizar
        if (producto.getCodigo() != null && !producto.getCodigo().isEmpty()) {
            productoFrontService.updateProducto(producto);
            flash.addFlashAttribute("success", "Producto creado con éxito");
        } else {
            productoFrontService.createProducto(producto);
            flash.addFlashAttribute("success", "Producto actualizado con éxito");
        }
        return "redirect:/producto/lista-producto";
    }

    /**
     * Modificar un producto existente
     */
    /*@GetMapping("/modificar-producto/{id}")
    public String modificarProducto(@PathVariable("id") String id, Model model) {
        Producto producto = productoFrontService.getProductoById(id);
        model.addAttribute("producto", producto);

        // Cargar categorías para el select
        List<Categoria> categorias = categoriaFrontService.getAll();
        model.addAttribute("categorias", categorias);

        model.addAttribute("accion", "Modificar producto");
        return "producto/alta-producto";
    }

    /**
     * Eliminar un producto
     */
    /*@GetMapping("/eliminar-producto/{id}")
    public String eliminarProducto(@PathVariable("id") String id, RedirectAttributes flash) {
        if(productoFrontService.deleteProducto(id)){
            flash.addFlashAttribute("success", "Categoría eliminada con éxito");
        } else {
            flash.addFlashAttribute("error", "Error al eliminar la categoría");
        }
        return "redirect:/producto/lista-producto";
    }
}*/
