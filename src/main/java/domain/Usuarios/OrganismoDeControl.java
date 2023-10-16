package domain.Usuarios;

import domain.entidades.Entidad;
import domain.servicios.Servicio;

import javax.persistence.*;

@Entity
@Table(name = "organismo_de_control")
public class OrganismoDeControl{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "correoElectronicoResponsable",nullable = false)
    private String correoElectronicoResponsable;

    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;



    public int getId() {
        return id;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public OrganismoDeControl(/*Usuario user,*/String username, String password , String correoElectronicoResponsable, String nombre, String descripcion) {
        this.usuario = new Usuario(username,password);
        //this.usuario = usuario;
        this.correoElectronicoResponsable = correoElectronicoResponsable;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public OrganismoDeControl() {
    }
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
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
