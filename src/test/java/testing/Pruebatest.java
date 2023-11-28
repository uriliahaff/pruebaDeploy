package testing;

import domain.Repositorios.RepositorioRol;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Permiso;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;



public class Pruebatest {
    @Test
    public void passwordInsegura()
    {
        Usuario unUsuario = new Usuario();
        unUsuario.setPassword("123456");
        Assertions.assertFalse(unUsuario.cumpleOWASP(unUsuario.getPassword()));
    }
    @Test
    public void passwordSegura()
    {
        Usuario unUsuario = new Usuario();
        unUsuario.setPassword("UnUsuario123!");
        Assertions.assertTrue(unUsuario.cumpleOWASP(unUsuario.getPassword()));
    }

    @Test
    public void passwordInseguraSinNumeros()
    {
        Usuario unUsuario = new Usuario();
        unUsuario.setPassword("UnUsuarioqqq!");
        Assertions.assertFalse(unUsuario.cumpleOWASP(unUsuario.getPassword()));
    }

    @Test
    public void passwordInseguraCorta()
    {
        Usuario unUsuario = new Usuario();
        unUsuario.setPassword("UnUsuario1!");
        Assertions.assertFalse(unUsuario.cumpleOWASP(unUsuario.getPassword()));
    }

    @Test
    public void permisos()
    {
        RepositorioRol repositorioRol = new RepositorioRol();

        Permiso permisoEntidad = new Permiso("editarEntidades");
        Permiso permisoComunidad = new Permiso("editarComunidad");
        Permiso permisoEditar = new Permiso("editarMiembro");
        Permiso permisoCrearEntidad = new Permiso("crearEntidad");
        Permiso permisoEditarUsuario = new Permiso("editarUsuario");
        Permiso permisoEditarRoles = new Permiso("editarRoles");


        repositorioRol.save(permisoEntidad);
        repositorioRol.save(permisoComunidad);
        repositorioRol.save(permisoEditar);
        repositorioRol.save(permisoEditarUsuario);
        repositorioRol.save(permisoEditarRoles);
    }
    @Test
    public void permisos2()
    {
        RepositorioRol repositorioRol = new RepositorioRol();
        Rol rol = repositorioRol.findRolByNombre("admin");
        rol.setPermisos(repositorioRol.findAllPermisos());
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
        repositorioUsuario.findUsuarioByUsername("miembroTestConMail").addRol(rol);
    }
    @Test
    public void add()
    {
        RepositorioRol repositorioRol = new RepositorioRol();
        Rol rol = repositorioRol.findRolByNombre("admin");
        Usuario usuario = RepositorioUsuario.findMiembrosByUserId(20).get(0).getUsuario();
        usuario.addRol(rol);
        new RepositorioUsuario().updateUsuario(usuario);

    }

    @Test
    public void passwordInseguraSinCaracteresEspeciales()
    {
        Usuario unUsuario = new Usuario();
        unUsuario.setPassword("UnUsuario123");
        Assertions.assertFalse(unUsuario.cumpleOWASP(unUsuario.getPassword()));
    }


}
