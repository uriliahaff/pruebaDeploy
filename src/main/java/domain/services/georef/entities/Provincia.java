package domain.services.georef.entities;

import javax.persistence.*;

@Entity (name = "provincia")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;
    // Constructor vacío requerido por Hibernate
    public Provincia() {
    }

    public Provincia(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
