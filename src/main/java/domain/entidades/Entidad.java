package domain.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entidad")
public class Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_entidad_id", nullable = false)
    private TipoEntidad tipo;

    @Column(name = "email")
    private String email;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "entidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Establecimiento> establecimientos;

    public Entidad(){}
    public Entidad(String nombre, String tipo, String email, String descripcion) {

        this.nombre = nombre;
        this.tipo = new TipoEntidad(tipo);
        this.email = email;
        this.descripcion = descripcion;
        this.establecimientos = new ArrayList<>();
    }
    public Entidad(String nombre, TipoEntidad tipo, String email, String descripcion) {

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
