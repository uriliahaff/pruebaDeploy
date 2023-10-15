package domain.services.service1;

import domain.Repositorios.RepositorioComunidad;
import domain.Usuarios.Comunidades.Comunidad;
import domain.services.service1.model.PropuestaDeFusionModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PropuestaDeFusion
{
    List<Comunidad> comunidades;
    LocalDateTime sugerenciaDate;

    public List<Comunidad> getComunidades() {
        return comunidades;
    }

    public LocalDateTime getSugerenciaDate() {
        return sugerenciaDate;
    }

    public PropuestaDeFusion(List<Comunidad> comunidades, LocalDateTime sugerenciaDate) {
        this.comunidades = comunidades;
        this.sugerenciaDate = sugerenciaDate;
    }
    public PropuestaDeFusion(PropuestaDeFusionModel propuestaDeFusionModel) {
        this(
                RepositorioComunidad.findComunidadByIds(
                        propuestaDeFusionModel.getComunidadesAFusionar().stream()
                                .map(comunidadModel -> (int)comunidadModel.id.intValue())
                                .collect(Collectors.toList())
                ),
                propuestaDeFusionModel.fechaPropuesta
        );
    }

}
