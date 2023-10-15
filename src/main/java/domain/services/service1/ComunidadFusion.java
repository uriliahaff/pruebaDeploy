package domain.services.service1;

import domain.Usuarios.Comunidades.Comunidad;
import domain.services.service1.model.ComunidadModel;
import domain.services.service1.model.PropuestaDeFusionModel;

import java.io.IOException;
import java.util.List;

public interface ComunidadFusion
{
    public List<PropuestaDeFusion> getSugerenciasDeFusion(List<Comunidad> comunidades, List<PropuestaDeFusion> propuestaDeFusionesPasadas) throws IOException;
    public Comunidad fusionarComunidades(PropuestaDeFusion propuestaDeFusion) throws IOException;
}
