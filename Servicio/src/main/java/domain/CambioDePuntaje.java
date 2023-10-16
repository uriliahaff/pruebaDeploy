package domain;

public class CambioDePuntaje {

    private Usuario usuario;
    private double puntaje;


    public CambioDePuntaje() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public CambioDePuntaje(Usuario usuario, double puntaje) {
        this.usuario = usuario;
        this.puntaje = puntaje;
    }
}