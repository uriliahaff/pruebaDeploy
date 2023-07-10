package domain.services.notificadorDeIncidentes;

import domain.Usuarios.Comunidades.Miembro;
import domain.informes.Incidente;

public abstract class CommandoNotificacion {

    protected Miembro miembro;
    protected Incidente incidente;
    public CommandoNotificacion ( Miembro miembro, Incidente incidente){
        this.miembro = miembro;
        this.incidente = incidente;
    }
    public abstract void notificarIncidente();

}
