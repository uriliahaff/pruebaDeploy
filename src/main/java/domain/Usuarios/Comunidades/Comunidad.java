package domain.Usuarios.Comunidades;

import domain.Usuarios.Usuario;
import domain.servicios.Servicio;

import java.util.List;

public class Comunidad {
    private String nombre;
    private List<Miembro> miembros;
    private List<Usuario> admins;
    private List<Servicio> intereses;

    public void altaMiembro(Miembro miembro)
    {
        miembros.add(miembro);
    }
    public void bajaMiembro(Miembro miembro)
    {
        miembros.remove(miembro);
    }

    public List<Miembro> miembrosFiltradosPorInteresEnLocalizacion(String lugar)
    {
        return miembros.stream()
                .filter(miembro -> miembro.lugarDeInteres(lugar))
                .toList();
    }

    public boolean deInteres(Servicio servicio)
    {
        return intereses.contains(servicio);
    }


}
