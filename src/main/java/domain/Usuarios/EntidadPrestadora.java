package domain.Usuarios;


import domain.entidades.Entidad;
import javax.persistence.*;

@Entity
@Table(name = "entidad_prestadora")
public class EntidadPrestadora extends Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidad_id", nullable = false)
    private Entidad entidad;

    /*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;*/

    @Column(nullable = false)
    private String correoElectronicoResponsable;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    public EntidadPrestadora() {
    }

    // Métodos "getters"
    public int getId() {
        return id;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    /*public Usuario getUsuario() {
        return usuario;
    }*/

    public String getCorreoElectronicoResponsable() {
        return correoElectronicoResponsable;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}