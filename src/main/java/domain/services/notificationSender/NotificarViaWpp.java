package domain.services.notificationSender;


import domain.Usuarios.Comunidades.Miembro;
import domain.services.wppSender.TwiloAdapter;
import domain.services.wppSender.WwpSender;

public class NotificarViaWpp implements ComponenteNotificador{
private WwpSender wwpSender;
    @Override
    public void enviarNotificacion(String titulo, Miembro destinatario, String message) {
        wwpSender = new TwiloAdapter();
        wwpSender.enviarWpp(titulo, destinatario.getTelefono(), message);
    }
}
