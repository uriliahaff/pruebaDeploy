package domain.services.emailSender;

public class EmailSenderAdapter implements EmailSender {
    private final SmtpEmailSender smtpEmailSender;

    public EmailSenderAdapter() {
        smtpEmailSender = new SmtpEmailSender("usuario", "password");
    }

    public void enviarMail(String titulo, String destinatario, String mensaje) {
        smtpEmailSender.enviarMail(titulo, destinatario, mensaje);
    }

}
