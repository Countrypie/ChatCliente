package paquete.chatcliente;

//Clase que encapsula los datos relativos a un mensaje

import java.time.LocalTime;

public class Mensaje {

    private String contenido;
    private String remitente;
    private LocalTime hora;

    public Mensaje(String contenido, String remitente) {
        this.contenido = contenido;
        this.remitente = remitente;
        this.hora=LocalTime.now();
    }

    public String getContenido() {
        return contenido;
    }

    public String getRemitente() {
        return remitente;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    
    
}
