package domain.services.emailSender;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class EmailSenderAdapter implements EmailSender {
    private final SmtpEmailSender smtpEmailSender;

    public EmailSenderAdapter() {
        smtpEmailSender = new SmtpEmailSender("markmarker539@gmail.com", "nhhk grcm qqco vtfe");
    }

    public void enviarMail(String titulo, String destinatario, String mensaje) {
        try {

            smtpEmailSender.enviarMail(titulo, destinatario, mensaje);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

}
