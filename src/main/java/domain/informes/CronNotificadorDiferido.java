package domain.informes;
import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;

import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

public class CronNotificadorDiferido {
    private static CronNotificadorDiferido instance = null;
    private final Timer timer = new Timer();

    private CronNotificadorDiferido() {
    }

    public static CronNotificadorDiferido getInstance() {
        if (instance == null) {
            instance = new CronNotificadorDiferido();
        }
        return instance;
    }

    public void agregarNotificacion(Incidente incidente, Miembro miembro)
    {
        String texto = incidente.getDescripcion();
        String titulo = "Al servicio " + incidente.getServicioAfectado().getServicio().getNombre() + " le paso algo";
        //String destinatario = miembro.getCorreoElectronico();
        ConfiguracionNotificacionDeIncidentes config = miembro.getConfiguracionNotificacionDeIncidentes();
        timer.schedule(new NotificacionDiferida(texto, titulo, miembro), (long) getNextScheduledTime(config.getHorarioPreferencia()) * 60 * 1000);
    }



    //En este código, primero obtenemos el tiempo actual y lo convertimos a formato float.
    // Luego, filtramos los horarios que son posteriores a la hora actual y los ordenamos.
    // Si hay horarios posteriores a la hora actual, devolvemos el primer horario de la lista ordenada (es decir, el más próximo a la hora actual).
    // Si no hay horarios posteriores a la hora actual, devolvemos el horario más temprano del día siguiente.
    public int getNextScheduledTime(List<Float> horarios) {
        LocalTime now = LocalTime.now();

        float nowAsFloat = now.getHour() + now.getMinute() / 60f;

        List<Float> horariosPostNow = horarios.stream()
                .filter(h -> h > nowAsFloat)
                .sorted()
                .collect(Collectors.toList());

        if (!horariosPostNow.isEmpty()) {
            return convertToMinutes(horariosPostNow.get(0)) - now.toSecondOfDay() / 60;
        } else {
            int minutesUntilEndOfDay = 1440 - now.toSecondOfDay() / 60;
            return minutesUntilEndOfDay + convertToMinutes(horarios.stream()
                    .min(Float::compare)
                    .orElseThrow());
        }
    }


    private int convertToMinutes(float time) {
        int hours = (int) time;
        int minutes = Math.round((time - hours) * 60);
        return hours * 60 + minutes;
    }
}
