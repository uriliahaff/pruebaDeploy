package testing;

import domain.Repositorios.RepositorioRol;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class createAdmin
{
    RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    @Test
    public void crearAdmin() {
        Usuario user = repositorioUsuario.findUsuarioByUsername("Admin");
        if(user==null)
        {
            user = new Usuario("Admin", "admin");
            repositorioUsuario.saveUsuario(user);
        }
        if(user.getRoles().size() < 2)
        {
            crearRol();
            crearRolEntidad();
            otra();
            user.addRol(new RepositorioRol().findRolByNombre("admin"));
            user.addRol(new RepositorioRol().findRolByNombre("adminEntidad"));
            repositorioUsuario.updateUsuario(user);
        }
    }
    @Test
    public void crearRol()
    {
        Rol rol = new Rol("admin",new ArrayList<>());
        new RepositorioRol().save(rol);

    }
    @Test
    public void crearRolEntidad()
    {
        Rol rol = new Rol("adminEntidad",new ArrayList<>());
        new RepositorioRol().save(rol);

    }

    @Test
    public void otra()
    {
        Usuario user = repositorioUsuario.findUsuarioByUsername("Admin");
        Rol rol = new Rol("adminOrganismo",new ArrayList<>());
        new RepositorioRol().save(rol);
    }
}
