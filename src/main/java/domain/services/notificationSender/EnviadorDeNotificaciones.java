package domain.services.notificationSender;

import domain.Usuarios.Comunidades.Miembro;

public class EnviadorDeNotificaciones {
private ComponenteNotificador estrategia;

public void enviarNotificacion(String titulo, Miembro destinatario, String mensaje){
    estrategia.enviarNotificacion(titulo, destinatario, mensaje);
}

public void cambiarEstrategia(ComponenteNotificador estrategia){
this.estrategia = estrategia;
}


}
