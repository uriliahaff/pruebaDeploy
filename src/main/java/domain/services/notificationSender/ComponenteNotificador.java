package domain.services.notificationSender;

import domain.Usuarios.Comunidades.Miembro;

public interface ComponenteNotificador {
    public void enviarNotificacion(String titulo, Miembro miembro, String message);
    public String name();
}
