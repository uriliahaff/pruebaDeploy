package domain.Usuarios.Comunidades;

import domain.notificaciones.ComponenteNotificador;

import java.text.SimpleDateFormat;
import java.util.Date;
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
}
