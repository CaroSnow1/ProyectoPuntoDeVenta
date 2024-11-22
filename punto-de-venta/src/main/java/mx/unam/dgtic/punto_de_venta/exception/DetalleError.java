package mx.unam.dgtic.punto_de_venta.exception;

import java.time.LocalDateTime;

public class DetalleError {
    private String estatusCode;
    private String mensaje;
    private String detalle;
    private LocalDateTime timeStamp;

    public DetalleError() {
    }

    public DetalleError(String estatusCode, String mensaje, String detalle, LocalDateTime timeStamp) {
        this.estatusCode = estatusCode;
        this.mensaje = mensaje;
        this.detalle = detalle;
        this.timeStamp = timeStamp;
    }

    public String getEstatusCode() {
        return estatusCode;
    }

    public void setEstatusCode(String estatusCode) {
        this.estatusCode = estatusCode;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

}

