package domain.services.wppSender;

import domain.services.emailSender.EmailSender;
import domain.services.emailSender.SmtpEmailSender;

public class TwiloAdapter implements WwpSender {
    private final TwiloWppSender twiloWppSender;

    public TwiloAdapter() {
        twiloWppSender = new TwiloWppSender();
    }

    public void enviarWpp(String titulo, String destinatario, String mensaje) {
        twiloWppSender.enviarWpp(titulo, destinatario, mensaje);
    }

}
