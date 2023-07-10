package domain.services.notificationSender;

import domain.Usuarios.Comunidades.Miembro;
import domain.services.emailSender.EmailSender;
import domain.services.emailSender.EmailSenderAdapter;

public class NotificarViaCorreo implements ComponenteNotificador{
private EmailSender emailSender;
    @Override
    public void enviarNotificacion(String titulo, Miembro destinatario, String message) {
        emailSender = new EmailSenderAdapter();
        emailSender.enviarMail(titulo, destinatario.getCorreoElectronico(), message);
    }
}
