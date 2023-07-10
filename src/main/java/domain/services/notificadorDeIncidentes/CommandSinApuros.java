package domain.services.notificadorDeIncidentes;

import domain.Usuarios.Comunidades.Miembro;
import domain.informes.CronNotificadorDiferido;
import domain.informes.Incidente;
import domain.informes.NotificacionDiferida;

public class CommandSinApuros extends CommandoNotificacion{
    public CommandSinApuros(Miembro miembro, Incidente incidente) {
        super(miembro, incidente);
    }

    @Override
    public void notificarIncidente() {

        CronNotificadorDiferido.getInstance().agregarNotificacion(incidente, miembro);

    }
}
