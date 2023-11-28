package testing;

import domain.Repositorios.RepositorioRol;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Permiso;
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

        repositorioRol.save(permisoEntidad);
        repositorioRol.save(permisoComunidad);
        repositorioRol.save(permisoEditar);
        repositorioRol.save(permisoCrearEntidad);
    }

    @Test
    public void passwordInseguraSinCaracteresEspeciales()
    {
        Usuario unUsuario = new Usuario();
        unUsuario.setPassword("UnUsuario123");
        Assertions.assertFalse(unUsuario.cumpleOWASP(unUsuario.getPassword()));
    }


}
