package domain.Usuarios;


import domain.entidades.Entidad;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "entidad_prestadora")
public class EntidadPrestadora{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidad_id", nullable = false)
    @Setter
    private Entidad entidad;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @MapsId
    @JoinColumn(name = "usuario_id", nullable = false)
    @Setter
    private Usuario usuario;

    @Column(name = "correo_electronico_responsable", nullable = false)
    @Setter
    private String correoElectronicoResponsable;

    @Column(name = "nombre", nullable = false)
    @Setter
    private String nombre;

    @Column(name = "descripcion")
    @Setter
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