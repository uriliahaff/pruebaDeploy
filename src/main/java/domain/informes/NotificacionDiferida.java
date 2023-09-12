package domain.informes;

import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;
import domain.services.notificationSender.EnviadorDeNotificaciones;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimerTask;

@Entity
@Table(name = "notificacion_diferida")
public class NotificacionDiferida extends TimerTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro destinatario;

    @Column
    private LocalDate fechaDeEnvio;

    public NotificacionDiferida() {
    }

    public NotificacionDiferida(String texto, String titulo, Miembro destinatario, LocalDate fechaDeEnvio) {
        this.texto = texto;
        this.titulo = titulo;
        this.destinatario = destinatario;
        this.fechaDeEnvio = fechaDeEnvio;
    }


    public void run()
    {
        EnviadorDeNotificaciones enviadorDeNotificaciones = EnviadorDeNotificaciones.getInstance();
        enviadorDeNotificaciones.cambiarEstrategia(destinatario.getConfiguracionNotificacionDeIncidentes().getMedioPreferido());
        enviadorDeNotificaciones.enviarNotificacion(titulo,destinatario,texto);
    }


}
