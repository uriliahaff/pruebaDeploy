package testing;

import domain.humanos.Usuario;
import domain.other.ValidadorDePassword;
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
    public void passwordInseguraSinCaracteresEspeciales()
    {
        Usuario unUsuario = new Usuario();
        unUsuario.setPassword("UnUsuario123");
        Assertions.assertFalse(unUsuario.cumpleOWASP(unUsuario.getPassword()));
    }


}
