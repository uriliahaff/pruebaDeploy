package domain;

public class Usuario {
    private Integer id;
    private double cambioDePuntaje = 0;

    public Usuario()
    {}
    public Usuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public double getCambioDePuntaje() {
        return cambioDePuntaje;
    }
}
