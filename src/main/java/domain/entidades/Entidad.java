package domain.entidades;

import java.util.ArrayList;
import java.util.List;

public class Entidad {
    private int id;
    private String nombre;
    private String tipo;
    private String email;
    private String descripcion;
    private List<Establecimiento> establecimientos;

    public Entidad(int id, String nombre, String tipo, String email, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.email = email;
        this.descripcion = descripcion;
        this.establecimientos = new ArrayList<>();
    }



    // Métodos getter y setter

    public String getNombre(){return nombre;}

    @Override
    public String toString() {
        return "Entidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", email='" + email + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
    public void agregarEstablecimiento(Establecimiento establecimiento) {
        establecimientos.add(establecimiento); // Agregar un establecimiento a la lista de establecimientos
    }

}
