package domain.services.notificationSender;

public interface ComponenteNotificador {
    public void enviarNotificacion(String titulo, String destinatario, String message);
}
