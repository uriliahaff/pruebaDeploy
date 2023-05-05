package testing;

import domain.humanos.Usuario;
import domain.other.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContraseniaTest {
    ChequeoNumero cn;
    ChequeoDiezMilContrasenias cdmc;
    ChequeoMayuscula cm;
    ChequeoLongitud cl;
    ChequeoCaracterEspecial cce;
    @BeforeEach
    public void setUp()
    {
        cn = new ChequeoNumero();
        cdmc = new ChequeoDiezMilContrasenias();
        cm = new ChequeoMayuscula();
        cl = new ChequeoLongitud();
        cce = new ChequeoCaracterEspecial();

    }
    @Test
    public void ChequeoCaracterEspecialFallo()
    {
        String contrasenia = "Hola";
        Assertions.assertFalse(cce.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoCaracterEspecialAcierto()
    {
        String contrasenia = "hola!";
        Assertions.assertTrue(cce.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoLongitudFallo()
    {
        String contrasenia = "hola";
        Assertions.assertFalse(cl.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoLongitudAcierto()
    {
        String contrasenia = "holaaaaaaaaa";
        Assertions.assertTrue(cl.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoMayusculaFallo()
    {
        String contrasenia = "hola";
        Assertions.assertFalse(cm.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoMayusculaAcierto()
    {
        String contrasenia = "Hola";
        Assertions.assertTrue(cm.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoDiezMilContraseñasFallo()
    {
        String contrasenia = "password";
        Assertions.assertFalse(cdmc.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoDiezMilContraseñasAcierto()
    {
        String contrasenia = "password1q";
        Assertions.assertTrue(cdmc.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoNumerosFallo()
    {
        String contrasenia = "hola";
        Assertions.assertFalse(cn.validadorContrasenia((contrasenia)));
    }
    @Test
    public void ChequeoNumerosAcierto()
    {
        String contrasenia = "hola1";
        Assertions.assertTrue(cn.validadorContrasenia((contrasenia)));
    }
    @Test
    public void chequeoValidarContrasenia()
    {
        ValidadorDePassword vp = new ValidadorDePassword();
        vp.addValidaciones(cm);
        vp.addValidaciones(cce);
        vp.addValidaciones(cdmc);
        vp.addValidaciones(cl);

        String contrasenia = "Holaholahola!";

        Assertions.assertTrue(vp.validarContrasenia((contrasenia)));

    }
}
