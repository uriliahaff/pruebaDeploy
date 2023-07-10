package domain.services.notificationSender;

public class EnviadorDeNotificaciones {
private ComponenteNotificador estrategia;

public void enviarNotificacion(String titulo, String destinatario, String mensaje){
    estrategia.enviarNotificacion(titulo, destinatario, mensaje);
}

public void cambiarEstrategia(ComponenteNotificador estrategia){
this.estrategia = estrategia;
}


}
