package testing;

import domain.humanos.Usuario;
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


}
