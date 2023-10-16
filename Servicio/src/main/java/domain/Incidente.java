package domain;

import java.time.LocalDateTime;

public class Incidente {
    private Integer prestacionDeServicio;
    private String apertura;
    private String cierre;
    private Usuario usuarioApertura;
    private Usuario usuarioAnalizador;

    public Usuario getUsuarioAnalizador() {
        return usuarioAnalizador;
    }

    public Incidente(){}
    public String getApertura() {
        return this.apertura;
    }

    public String getCierre() {
        return this.cierre;
    }

    public Usuario getUsuarioApertura() {
        return this.usuarioApertura;
    }

    public Usuario getUsuarioCierre() {
        return this.usuarioAnalizador;
    }

    public Integer getPrestacionDeServicio(){
        return this.prestacionDeServicio;
    }
}