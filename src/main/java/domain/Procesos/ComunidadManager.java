package domain.Procesos;

import domain.Repositorios.RepositorioComunidad;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.services.service1.ComunidadFusionAdapter;
import domain.services.service1.PropuestaDeFusion;

import java.io.IOException;

public class ComunidadManager
{
    private static RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    private static RepositorioComunidad repositorioComunidad = new RepositorioComunidad();

    public static void agregarMiembro(Miembro miembro, Comunidad comunidad)
    {
        comunidad.altaMiembro(miembro);
        miembro.addComunidad(comunidad);

        repositorioUsuario.updateMiembro(miembro);
        repositorioComunidad.update(comunidad);
    }
    public void fusionarComunidades(PropuestaDeFusion propuestaDeFusion)
    {
        ComunidadFusionAdapter comunidadFusionAdapter = new ComunidadFusionAdapter();
        try
        {
            Comunidad nuevaComunidad = comunidadFusionAdapter.fusionarComunidades(propuestaDeFusion);
            propuestaDeFusion.getComunidades().forEach(comunidad -> repositorioComunidad.delete(comunidad));
            nuevaComunidad.getMiembros().forEach(miembro -> miembro.addComunidad(nuevaComunidad));
        }
        catch (IOException e)
        {
            //Nose, bu ju.
        }

    }
}
