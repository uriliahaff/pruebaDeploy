package domain.services.service2.model;

import domain.Usuarios.Comunidades.Comunidad;

import java.util.List;

public class ComunidadModel
{
    private int id;

    private List<UsuarioModel> miembros;
    private double gradoDeConfianza;

    public List<UsuarioModel> getMiembros() {
        return miembros;
    }

    public int getId() {
        return id;
    }

    public double getGradoDeConfianza() {
        return gradoDeConfianza;
    }

    public ComunidadModel(Comunidad comunidad)
    {
        this.id = comunidad.getId();
        this.miembros = comunidad.getMiembros().stream()
                .map(miembro -> new UsuarioModel(miembro.getUsuario()))
                .toList();
        this.gradoDeConfianza = 0;
    }
}
