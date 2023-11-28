package domain.services.notificationSender;

import domain.Usuarios.Comunidades.Miembro;

public class EnviadorDeNotificaciones {
    private static EnviadorDeNotificaciones instance = null;
    //private ComponenteNotificador estrategia;

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

    public void enviarNotificacion(ComponenteNotificador estrategia, String titulo, Miembro destinatario, String mensaje){
        System.out.println("Enviando notificacion " + destinatario.getNombre());
        estrategia.enviarNotificacion(titulo, destinatario, mensaje);
    }

    //public void cambiarEstrategia(ComponenteNotificador estrategia){
    //    this.estrategia = estrategia;
    //}
}
