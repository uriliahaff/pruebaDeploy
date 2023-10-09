package domain;

public class Usuario {
    private Integer id;
    private double cambioDePuntaje = 0;

    public Usuario(Integer id) {
        this.id = id;
    }
    public void cambiarPuntaje(float cambio)
    {
        this.cambioDePuntaje+=cambio;
    }
}
