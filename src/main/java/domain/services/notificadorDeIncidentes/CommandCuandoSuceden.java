package domain.services.notificadorDeIncidentes;

import domain.Usuarios.Comunidades.Miembro;
import domain.informes.Incidente;
import domain.services.notificationSender.EnviadorDeNotificaciones;

public class CommandCuandoSuceden extends CommandoNotificacion{
    public CommandCuandoSuceden(Miembro miembro, Incidente incidente) {
        super(miembro, incidente);
    }
    private static EnviadorDeNotificaciones enviadorDeNotificaciones = EnviadorDeNotificaciones.getInstance();

    @Override
    public void notificarIncidente() {
        //enviadorDeNotificaciones.cambiarEstrategia(miembro.getConfiguracionNotificacionDeIncidentes().getMedioPreferido());
        enviadorDeNotificaciones.enviarNotificacion(
                miembro.getConfiguracionNotificacionDeIncidentes().getMedioPreferido()
                ,"Nuevo Incidente"
                , miembro
                ,"Incidente en "+incidente.getServicioAfectado()
        );
    }
}
