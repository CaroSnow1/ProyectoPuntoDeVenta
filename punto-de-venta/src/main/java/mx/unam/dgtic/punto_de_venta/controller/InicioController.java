package mx.unam.dgtic.punto_de_venta.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class InicioController {
    @Value("${spring.application.name}")
    String nombreApp;

    //Endpoint raiz
    @GetMapping("/")
    public String inicioPagina(Model model){
        SimpleDateFormat formato =  new SimpleDateFormat("dd/MM/yy");
        model.addAttribute("nombreAplicacion", nombreApp);
        model.addAttribute("fecha", formato.format(new Date()));

        return "inicio";
    }

    //End-point
    @GetMapping("/inicio")
    public String inicioPrincipal(Model model){
        model.addAttribute("contenido", "Inicio" );
        model.addAttribute("fecha", new Date() );
        model.addAttribute("nombreAplicacion", "Punto de Venta");
        return "principal";
    }


}
