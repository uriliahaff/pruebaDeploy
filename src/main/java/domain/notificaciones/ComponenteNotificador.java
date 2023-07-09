package domain.notificaciones;

public interface ComponenteNotificador {
    public void enviarNotificacion(String titulo, String destinatario, String message);
}
