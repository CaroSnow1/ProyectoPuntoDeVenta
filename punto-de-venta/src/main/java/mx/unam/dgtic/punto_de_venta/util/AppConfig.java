package mx.unam.dgtic.punto_de_venta.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
    @Bean
    public ResourceBundleMessageSource messageSource(){
        var source=new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    //Configurar un unico webclient builder como bean para usarlo con todas las apis
    @Bean
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }
}
