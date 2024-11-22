package mx.unam.dgtic.punto_de_venta.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {
    @ExceptionHandler(CategoriaNoExisteException.class)
    public ResponseEntity<DetalleError> errorDeRestriccion(
            CategoriaNoExisteException ex,
            HttpServletRequest request
    ){
        DetalleError detalle = new DetalleError();
        detalle.setMensaje(ex.getMessage());
        detalle.setDetalle("Error de catalogo de Categoria");
        detalle.setEstatusCode(HttpStatus.CONFLICT.toString());
        detalle.setTimeStamp((LocalDateTime.now()) );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalle);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> errorFormatoDeCliente(
            HttpMessageNotReadableException ex
    ){
        HashMap<String, String> detalles = new HashMap<>();
        detalles.put("mensaje", "El formato de los datos es incorrecto");
        detalles.put("detalle", ex.getMessage());
        detalles.put("timestamp", LocalDateTime.now().toString());

        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(detalles);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratamientoValidacion(
            MethodArgumentNotValidException ex
    ){
        HashMap<String, Object> detalles = new HashMap<>();
        detalles.put("mensaje", "Error de validacion de campos, favor de revisar");
        detalles.put("statusCode", ex.getStatusCode());
        detalles.put("timestamp", LocalDateTime.now().toString());

        HashMap<String, String> detalleCampos = new HashMap<>();

        int i = 1;
        for(FieldError campoError : ex.getBindingResult().getFieldErrors()){
            //Nombre error, detalles error
            detalleCampos.put(campoError.getField() + i++, campoError.getDefaultMessage());
        }

        detalles.put("errores",detalleCampos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalles);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<DetalleError> recursoNoExiste(
            NoResourceFoundException ex,
            HttpServletRequest request
    ){
        DetalleError detalleError = new DetalleError();
        detalleError.setMensaje("Ese recurso no existe: " +  request.getRequestURI());
        detalleError.setTimeStamp(LocalDateTime.now());
        detalleError.setEstatusCode(HttpStatus.NOT_FOUND.toString());
        detalleError.setDetalle(request.getRequestURI() + "- " + request.getContextPath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalleError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DetalleError> errorConversion(
            MethodArgumentTypeMismatchException ex
    ){
        DetalleError detalle = new DetalleError();
        detalle.setMensaje(ex.getMessage());
        detalle.setEstatusCode(HttpStatus.BAD_REQUEST.toString());
        detalle.setTimeStamp(LocalDateTime.now());
        detalle.setDetalle("Propiedad: "+ ex.getPropertyName() + " Tipo de dato: " + ex.getRequiredType());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(detalle);
    }
}
