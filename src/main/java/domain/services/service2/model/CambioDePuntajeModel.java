package domain.services.service2.model;

import domain.services.service2.CambioDePuntaje;
import domain.services.service2.model.UsuarioModel;

public class CambioDePuntajeModel
{
    private UsuarioModel usuario;
    private double puntaje;

    public UsuarioModel getUsuario() {
        return usuario;
    }
    public CambioDePuntajeModel()
    {}
    public double getPuntaje() {
        return puntaje;
    }
}
