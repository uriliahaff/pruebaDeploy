package domain.services.service2;

import domain.Repositorios.RepositorioMiembro;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Usuario;
import domain.services.service2.model.CambioDePuntajeModel;

public class CambioDePuntaje
{
    private Usuario usuario;
    private double cambio;


    public CambioDePuntaje(CambioDePuntajeModel cambioDePuntajeModel) {
        this.usuario = new RepositorioUsuario().findUsuarioById(cambioDePuntajeModel.getUsuario().getId());
        this.cambio = cambioDePuntajeModel.getPuntaje();
    }

    public void apply()
    {
        this.usuario.addConfianza(cambio);
        new RepositorioUsuario().updateUsuario(this.usuario);
    }

    public double getCambio() {
        return cambio;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
