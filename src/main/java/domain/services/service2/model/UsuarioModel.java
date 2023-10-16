package domain.services.service2.model;

import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.Usuario;

public class UsuarioModel
{
    private Integer id;
    private double cambioDePuntaje = 0;

    public Integer getId() {
        return id;
    }

    public double getCambioDePuntaje() {
        return cambioDePuntaje;
    }

    public UsuarioModel(){}

    public UsuarioModel(Usuario usuario) {
        this.id = usuario.getId();
        this.cambioDePuntaje = usuario.getGradoDeConfianza();
    }
}
