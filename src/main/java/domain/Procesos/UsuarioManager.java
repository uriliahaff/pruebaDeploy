package domain.Procesos;

import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Usuario;

public class UsuarioManager {

    private static RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    public static boolean CrearUsuario(String username, String contraseña)
    {
        Usuario nuevoUsuario = new Usuario();
        if(nuevoUsuario.cumpleOWASP(contraseña))
        {
            repositorioUsuario.saveUsuario(nuevoUsuario);
        }
        return false;
    }

}
