package domain.Usuarios.Comunidades;

import domain.Usuarios.Usuario;
import domain.informes.Incidente;
import domain.services.Servicio;

import java.util.ArrayList;
import java.util.List;

public class Miembro extends Usuario{
    private String nombre;
    private String apellido;
    private String correoElectronico;

    private ConfiguracionNotificacionDeIncidentes configuracionNotificacionDeIncidentes;
    //TODO: Remover Usuario del Diagrama
    private List<Servicio> serviciosQueAfectan = new ArrayList<>();

    //TODO: Servicio de interes, entidad de interes,

    public boolean estaEnArea(String Loc)
    {
        return true;
    }

    public List<String> getLugaresDeInteres()
    {
        return new ArrayList<>();
    }
    public boolean lugarDeInteres(String lugar)
    {
        //TODO: hacer el chequeo real
        return true;
    }

    public void notificar(Incidente incidente)
    {
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
}
