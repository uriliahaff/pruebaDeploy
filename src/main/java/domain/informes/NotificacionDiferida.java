package domain.informes;

import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;
import domain.services.notificationSender.EnviadorDeNotificaciones;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimerTask;

@Entity
@Table(name = "notificacion_diferida")
public class NotificacionDiferida extends TimerTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "texto", nullable = false)
    private String texto;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro destinatario;

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public Miembro getDestinatario() {
        return destinatario;
    }

    public LocalDateTime getFechaDeEnvio() {
        return fechaDeEnvio;
    }

    public static EnviadorDeNotificaciones getEnviadorDeNotificaciones() {
        return enviadorDeNotificaciones;
    }

    @Column(name = "fecha_de_envio")
    private LocalDateTime fechaDeEnvio;

    @Transient
    private static EnviadorDeNotificaciones enviadorDeNotificaciones = EnviadorDeNotificaciones.getInstance();

    public NotificacionDiferida() {
        if(enviadorDeNotificaciones == null)
            enviadorDeNotificaciones = EnviadorDeNotificaciones.getInstance();
    }

    public NotificacionDiferida(String texto, String titulo, Miembro destinatario, LocalDateTime fechaDeEnvio) {
        this.texto = texto;
        this.titulo = titulo;
        this.destinatario = destinatario;
        this.fechaDeEnvio = fechaDeEnvio;
    }


    public void run()
    {
        //enviadorDeNotificaciones.cambiarEstrategia(destinatario.getConfiguracionNotificacionDeIncidentes().getMedioPreferido());
        enviadorDeNotificaciones.enviarNotificacion(
                destinatario.getConfiguracionNotificacionDeIncidentes().getMedioPreferido()
                ,titulo
                ,destinatario
                ,texto
        );
    }


}
