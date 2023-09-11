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
public class Miembro extends Usuario{
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String correoElectronico;
    @Column
    private String telefono;

    @Transient
    private ConfiguracionNotificacionDeIncidentes configuracionNotificacionDeIncidentes;
    //TODO: Remover Usuario del Diagrama

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_id")
    private List<Servicio> serviciosQueAfectan = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_id")
    private List<Comunidad> comunidades = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "miembro_id")
    private List<Direccion> lugaresDeInteres = new ArrayList<>();


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
