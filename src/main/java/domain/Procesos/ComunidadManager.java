package domain.Procesos;

import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;

public class ComunidadManager
{
    private static RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    public static void agregarMiembro(Miembro miembro, Comunidad comunidad)
    {
        comunidad.altaMiembro(miembro);
        miembro.addComunidad(comunidad);

        repositorioUsuario.updateMiembro(miembro);
        //TODO:Update
    }
}
