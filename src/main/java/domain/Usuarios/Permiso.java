package domain.Usuarios;

import javax.persistence.*;

@Entity
@Table(name = "permiso")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    // Constructor vacío para Hibernate
    public Permiso() {
    }

    // Constructor completo
    public Permiso(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}