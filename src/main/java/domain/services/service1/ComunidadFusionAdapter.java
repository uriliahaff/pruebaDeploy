package domain.services.service1;

import domain.Repositorios.RepositorioComunidad;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Usuario;
import domain.services.service1.model.ComunidadModel;
import domain.services.service1.model.PeticionModel;
import domain.services.service1.model.PropuestaDeFusionModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComunidadFusionAdapter implements ComunidadFusion {
    private static ComunidadApiService comunidadApiService = ComunidadApiService.getInstancia();

    @Override
    public List<PropuestaDeFusion> getSugerenciasDeFusion(List<Comunidad> comunidades, List<PropuestaDeFusion> propuestaDeFusionesPasadas) throws IOException {

        System.out.println(comunidades.size());
        List<ComunidadModel> comunidadModels = comunidades.stream()
                .map(ComunidadModel::new)
                .toList();
        List<PropuestaDeFusionModel> propuestaDeFusionModels = propuestaDeFusionesPasadas.stream()
                .map(PropuestaDeFusionModel::new)
                .toList();
        PeticionModel peticionModel = new PeticionModel(propuestaDeFusionModels, comunidadModels);

        List<PropuestaDeFusionModel> propuestasNuevas = comunidadApiService.obtenerPropuestas(peticionModel);

        return propuestasNuevas.stream().map(PropuestaDeFusion::new).toList();
    }

    @Override
    public Comunidad fusionarComunidades(PropuestaDeFusion propuestaDeFusion) throws IOException {
        PropuestaDeFusionModel propuestaDeFusionModel = new PropuestaDeFusionModel(propuestaDeFusion);
        ComunidadModel comunidadModel = comunidadApiService.fusionarComunidades(propuestaDeFusionModel);
        List<Usuario> admins = propuestaDeFusion.getComunidades()
                .stream()
                .flatMap(comunidad -> comunidad.getAdmins().stream())
                .collect(Collectors.toList());
        return comunidadModel.fromModelToEntity(comunidadModel, admins);
    }

}
