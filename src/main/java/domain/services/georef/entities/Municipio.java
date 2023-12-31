package domain.services.georef.entities;

import javax.persistence.*;

@Entity //(name = "municipio")
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @Column(nullable = false)
    private String nombre;

    public Municipio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Municipio() {
    }

    public Municipio setId(int id)
    {
        this.id = id;
        return this;
    }
}
