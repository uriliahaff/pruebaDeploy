package domain.services.emailSender;

public interface EmailSender {
    void enviarMail(String titulo, String destinatario, String mensaje);

}
