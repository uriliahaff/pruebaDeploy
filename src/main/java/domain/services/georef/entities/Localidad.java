package domain.services.georef.entities;

import javax.persistence.*;

@Entity
public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    public int getId() {
        return id;
    }

    public Localidad() {
    }

    public String getNombre() {
        return nombre;
    }

    public Localidad(String nombre) {
        this.nombre = nombre;
    }
}
