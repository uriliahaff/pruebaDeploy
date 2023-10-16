package domain.services.service2.model;

import domain.Usuarios.Comunidades.Comunidad;

import java.util.List;

public class ComunidadModel
{
    private List<UsuarioModel> miembros;
    private double gradoDeConfianza;

    public ComunidadModel(Comunidad comunidad)
    {
        this.miembros = comunidad.getMiembros().stream()
                .map(miembro -> new UsuarioModel(miembro.getUsuario()))
                .toList();
        this.gradoDeConfianza = 0;
    }
}
