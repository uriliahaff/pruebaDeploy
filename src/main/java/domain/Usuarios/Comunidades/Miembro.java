package domain.Usuarios.Comunidades;

import domain.Usuarios.Usuario;
import domain.informes.Incidente;
import domain.localizaciones.Direccion;
import domain.services.notificadorDeIncidentes.CommandoNotificacion;
import domain.servicios.Servicio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "miembro")
public class Miembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "correoElectronico", nullable = false)
    private String correoElectronico;

    @Column(name = "telefono", nullable = false)

    private String telefono;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "configuracion_id")
    private ConfiguracionNotificacionDeIncidentes configuracionNotificacionDeIncidentes;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_id")
    private List<Servicio> serviciosQueAfectan = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_id")
    private List<Comunidad> comunidades = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_id")
    private List<Direccion> lugaresDeInteres = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructor vacío para Hibernate
    public Miembro() {
    }

    // Constructor completo
    public Miembro(String nombre, String apellido, String correoElectronico, String telefono,
                   ConfiguracionNotificacionDeIncidentes configuracionNotificacionDeIncidentes,
                   Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.configuracionNotificacionDeIncidentes = configuracionNotificacionDeIncidentes;
        this.usuario = usuario;
    }

    public List<Comunidad> getComunidades() {
        return comunidades;
    }
//TODO: Servicio de interes, entidad de interes,

    public boolean estaEnArea(String Loc)
    {
        return true;
    }

    public List<Direccion> getLugaresDeInteres()
    {
        return this.lugaresDeInteres;
    }
    public boolean lugarDeInteres(Direccion lugar)
    {
        return lugaresDeInteres.stream().anyMatch(l -> l.sameLocation(lugar));
    }

    public CommandoNotificacion getCommandoNotificar(Incidente incidente)
    {
        return configuracionNotificacionDeIncidentes.generarComando(this,incidente);
        //TODO: Aca tiene que hacer toda la cosa con el comand y no se que
    }

    public boolean leAfecta(Servicio servicio)
    {
        return serviciosQueAfectan.contains(servicio);
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public ConfiguracionNotificacionDeIncidentes getConfiguracionNotificacionDeIncidentes() {
        return configuracionNotificacionDeIncidentes;
    }

    public String getTelefono() {return telefono;}
}
