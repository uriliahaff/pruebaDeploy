package domain.Procesos;

import domain.Repositorios.RepositorioComunidad;
import domain.Repositorios.RepositorioPropuestasDeFusion;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.services.service1.ComunidadFusionAdapter;
import domain.services.service1.PropuestaDeFusion;

import java.io.IOException;
import java.util.List;

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
    public List<PropuestaDeFusion> propuestaDeFusions(List<Comunidad> comunidades) throws IOException {
        ComunidadFusionAdapter comunidadFusionAdapter = new ComunidadFusionAdapter();
        List<PropuestaDeFusion> propuestaDeFusions = comunidadFusionAdapter.getSugerenciasDeFusion(comunidades, new RepositorioPropuestasDeFusion().findAll());
        new RepositorioPropuestasDeFusion().saveAll(propuestaDeFusions);
        return propuestaDeFusions;
    }
    public void fusionarComunidades(PropuestaDeFusion propuestaDeFusion) throws IOException {
        ComunidadFusionAdapter comunidadFusionAdapter = new ComunidadFusionAdapter();

            Comunidad nuevaComunidad = comunidadFusionAdapter.fusionarComunidades(propuestaDeFusion);
            propuestaDeFusion.getComunidades().forEach(comunidad -> repositorioComunidad.delete(comunidad));
            nuevaComunidad.getMiembros().forEach(miembro -> miembro.addComunidad(nuevaComunidad));


    }
}
