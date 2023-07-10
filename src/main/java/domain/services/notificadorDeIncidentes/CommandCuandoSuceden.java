package domain.services.notificadorDeIncidentes;

import domain.Usuarios.Comunidades.Miembro;
import domain.informes.Incidente;

public class CommandCuandoSuceden implements CommandoNotificacion{
    @Override
    public void notificarIncidente(Miembro miembro, Incidente incidente) {
        miembro.getConfiguracionNotificacionDeIncidentes().getMedioPreferido().enviarNotificacion("Nuevo Incidente",miembro, "Incidente en "+incidente.getServicioAfectado());
    }
}
