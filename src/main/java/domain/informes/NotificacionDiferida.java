package domain.informes;

import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;
import domain.services.notificationSender.EnviadorDeNotificaciones;

import java.util.TimerTask;

public class NotificacionDiferida extends TimerTask {
    private String texto;
    private String titulo;
    private Miembro destinatario;

    public NotificacionDiferida(String texto, String titulo, Miembro destinatario) {
        this.texto = texto;
        this.titulo = titulo;
        this.destinatario = destinatario;
    }

    public void run()
    {
        EnviadorDeNotificaciones enviadorDeNotificaciones = EnviadorDeNotificaciones.getInstance();
        enviadorDeNotificaciones.cambiarEstrategia(destinatario.getConfiguracionNotificacionDeIncidentes().getMedioPreferido());
        enviadorDeNotificaciones.enviarNotificacion(titulo,destinatario,texto);
    }


}
