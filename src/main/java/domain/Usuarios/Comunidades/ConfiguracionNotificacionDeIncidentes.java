package domain.Usuarios.Comunidades;

import domain.informes.Incidente;
import domain.services.notificadorDeIncidentes.CommandCuandoSuceden;
import domain.services.notificadorDeIncidentes.CommandSinApuros;
import domain.services.notificadorDeIncidentes.CommandoNotificacion;
import domain.services.notificationSender.ComponenteNotificador;

import java.util.List;

public class ConfiguracionNotificacionDeIncidentes {
    private ComponenteNotificador medioPreferido;
    private List<Float> horarioPreferencia;
    private float toleranceInMinutes = 5.0f;
    public ComponenteNotificador getMedioPreferido() {
        return medioPreferido;
    }


    public List<Float> getHorarioPreferencia() {
        return horarioPreferencia;
    }
    public CommandoNotificacion generarComando(Miembro miembro, Incidente incidente)
    {
        if (horarioPreferencia.isEmpty())
        {
            return new CommandCuandoSuceden(miembro, incidente);
        }
        else
        {
            return new CommandSinApuros(miembro, incidente);
        }
    }
}
