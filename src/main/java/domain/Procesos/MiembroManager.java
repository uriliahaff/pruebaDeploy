package domain.Procesos;

import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.Usuario;
import domain.services.notificationSender.ComponenteNotificador;

public class MiembroManager
{
    private static RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    public void crearMiembro(String nombre, String apellido, String email, String telefono, ComponenteNotificador medioPreferido, Usuario usuario)
    {
        Miembro nuevoMiembro = new Miembro(nombre, apellido, email, telefono, new ConfiguracionNotificacionDeIncidentes(medioPreferido), usuario);
        repositorioUsuario.saveMiembro(nuevoMiembro);
    }
}
