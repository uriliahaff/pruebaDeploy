package domain.services.notificationSender;

import domain.Usuarios.Comunidades.Miembro;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class EnviadorDeNotificaciones {
    private static EnviadorDeNotificaciones instance = null;
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // Un solo hilo para manejar las notificaciones

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
        CompletableFuture.runAsync(() -> {
            System.out.println("Enviando notificacion a " + destinatario.getNombre());
            estrategia.enviarNotificacion(titulo, destinatario, mensaje);
        }, executorService).exceptionally(e -> {
            System.err.println("Error al enviar notificación: " + e.getMessage());
            return null;
        });
    }

    // Opcionalmente, puedes proporcionar un método para cerrar el servicio de ejecución
    public void shutdown() {
        executorService.shutdown();
    }

    //public void cambiarEstrategia(ComponenteNotificador estrategia){
    //    this.estrategia = estrategia;
    //}
}
