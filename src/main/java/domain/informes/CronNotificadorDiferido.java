package domain.informes;
import domain.Repositorios.RepositorioNotificacionDiferida;
import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;
import domain.other.EntityManagerProvider;
import domain.services.notificadorDeIncidentes.NotificadorDeIncidentes;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class CronNotificadorDiferido {
    private static CronNotificadorDiferido instance = null;
    private final Timer timer = new Timer();

    //private CronNotificadorDiferido() {
    //}
    private CronNotificadorDiferido() {
        // Tarea que se ejecutará cada 5 minutos (300000 milisegundos)
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                executeAndDeleteExpiredNotifications();
            }
        };
        timer.scheduleAtFixedRate(repeatedTask, 0, 300000); // Empieza inmediatamente y repite cada 5 min
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
        NotificacionDiferida notificacionDiferida = new NotificacionDiferida(
                texto
                , titulo
                , miembro
                ,getNextScheduledDate(config.getHorarioPreferencia()));
        new RepositorioNotificacionDiferida().save(notificacionDiferida);
        //timer.schedule(new NotificacionDiferida(texto, titulo, miembro), (long) getNextScheduledTime(config.getHorarioPreferencia()) * 60 * 1000);

    }


    public LocalDateTime getNextScheduledDate(List<Float> horarios) {
        LocalDateTime now = LocalDateTime.now();
        float nowAsFloat = now.getHour() + now.getMinute() / 60f;

        List<Float> horariosPostNow = horarios.stream()
                .filter(h -> h > nowAsFloat)
                .sorted()
                .collect(Collectors.toList());

        if (!horariosPostNow.isEmpty()) {
            return convertToLocalDate(now, horariosPostNow.get(0));
        } else {
            return convertToLocalDate(now.plusDays(1), horarios.stream()
                    .min(Float::compare)
                    .orElseThrow());
        }
    }

    public void executeAndDeleteExpiredNotifications() {
        EntityManager em = EntityManagerProvider.getInstance().getEntityManager();
        em.getTransaction().begin();

        // Consulta para obtener todas las NotificacionDiferida con fechaEnvio pasada
        TypedQuery<NotificacionDiferida> query = em.createQuery(
                "SELECT n FROM NotificacionDiferida n WHERE n.fechaEnvio < :now",
                NotificacionDiferida.class
        );

        query.setParameter("now", LocalDate.now());

        // Obtener los resultados
        List<NotificacionDiferida> expiredNotifications = query.getResultList();

        // Ejecutar y eliminar cada NotificacionDiferida
        for (NotificacionDiferida notificacion : expiredNotifications) {
            notificacion.run();  // Ejecutar la tarea
            em.remove(notificacion);  // Eliminar de la base de datos
        }

        em.getTransaction().commit();
    }

    private LocalDateTime convertToLocalDate(LocalDateTime date, float timeAsFloat) {
        int hour = (int) timeAsFloat;
        int minute = (int) ((timeAsFloat - hour) * 60);
        // Crear una fecha con la hora y el minuto calculados
        // Esto asume que te refieres al próximo horario como el del mismo día o del día siguiente
        return date;
    }
/*
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
    }*/
}
