package domain.Usuarios;

import domain.other.*;

import java.util.List;

public class Usuario{
    private String username;
    private String password;

    private List<Rol> roles;

    //TODO: Disccutir como correjir esto
    private ValidadorDePassword vp = new ValidadorDePassword();

    public Usuario() {
        vp.addValidaciones(new ChequeoDiezMilContrasenias());
        vp.addValidaciones(new ChequeoMayuscula());
        vp.addValidaciones(new ChequeoLongitud());
        vp.addValidaciones(new ChequeoCaracterEspecial());
        vp.addValidaciones(new ChequeoNumero());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean cumpleOWASP(String password){
        return this.vp.validarContrasenia(password);
    }

}
