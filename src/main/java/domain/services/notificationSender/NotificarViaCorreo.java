package domain.services.notificationSender;

import domain.services.emailSender.EmailSender;
import domain.services.emailSender.EmailSenderAdapter;

public class NotificarViaCorreo implements ComponenteNotificador{
private EmailSender emailSender;
    @Override
    public void enviarNotificacion(String titulo, String destinatario, String message) {
        emailSender = new EmailSenderAdapter();
        emailSender.enviarMail(titulo, destinatario, message);
    }
}
