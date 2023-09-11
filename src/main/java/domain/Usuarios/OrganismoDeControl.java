package domain.Usuarios;

import javax.persistence.*;

@Entity
@Table(name = "organismo_de_control")
public class OrganismoDeControl extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private String correoElectronicoResponsable;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    // Métodos "getters"
    public int getId() {
        return id;
    }

    public OrganismoDeControl(String username, String password,Usuario usuario, String correoElectronicoResponsable, String nombre, String descripcion) {
        super(username,password);
        this.usuario = usuario;
        this.correoElectronicoResponsable = correoElectronicoResponsable;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public OrganismoDeControl() {
    }

    public Usuario getUsuario() {
        return usuario;
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
