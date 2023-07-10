package domain.services.notificadorDeIncidentes;

import domain.Usuarios.Comunidades.Miembro;
import domain.informes.Incidente;

public interface CommandoNotificacion {

    public void notificarIncidente(Miembro miembro, Incidente incidente);

}
