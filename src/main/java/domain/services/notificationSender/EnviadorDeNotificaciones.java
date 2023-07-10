package domain.services.notificationSender;

import domain.Usuarios.Comunidades.Miembro;

public class EnviadorDeNotificaciones {
    private static EnviadorDeNotificaciones instance = null;
    private ComponenteNotificador estrategia;

    // Hacer el constructor privado
    private EnviadorDeNotificaciones() {
    }

    // Proporcionar un método estático para obtener la instancia
    public static EnviadorDeNotificaciones getInstance() {
        if (instance == null) {
            instance = new EnviadorDeNotificaciones();
        }
        return instance;
    }

    public void enviarNotificacion(String titulo, Miembro destinatario, String mensaje){
        estrategia.enviarNotificacion(titulo, destinatario, mensaje);
    }

    public void cambiarEstrategia(ComponenteNotificador estrategia){
        this.estrategia = estrategia;
    }
}
