package domain.services.notificationSender;


import domain.services.wppSender.TwiloAdapter;
import domain.services.wppSender.WwpSender;

public class NotificarViaWpp implements ComponenteNotificador{
private WwpSender wwpSender;
    @Override
    public void enviarNotificacion(String titulo, String destinatario, String message) {
        wwpSender = new TwiloAdapter();
        wwpSender.enviarWpp(titulo, destinatario, message);
    }
}
