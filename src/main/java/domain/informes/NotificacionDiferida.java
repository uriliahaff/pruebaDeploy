package domain.informes;

import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;

import java.util.TimerTask;

public class NotificacionDiferida extends TimerTask {
    private String texto;
    private String titulo;
    private String destinatario;
    private ConfiguracionNotificacionDeIncidentes config;

    public NotificacionDiferida(String texto, String titulo, String destinatario, ConfiguracionNotificacionDeIncidentes config) {
        this.texto = texto;
        this.titulo = titulo;
        this.destinatario = destinatario;
        this.config = config;
    }

    public void run()
    {
        //TODO: enviar notificacion
    }


}
