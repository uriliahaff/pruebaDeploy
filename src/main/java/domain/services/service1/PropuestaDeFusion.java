package domain.services.service1;

import javax.persistence.*;

import domain.Repositorios.RepositorioComunidad;
import domain.Usuarios.Comunidades.Comunidad;
import domain.services.service1.model.PropuestaDeFusionModel;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PropuestaDeFusion
{
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "propuesta_comunidad",
            joinColumns = @JoinColumn(name = "propuesta_id"),
            inverseJoinColumns = @JoinColumn(name = "comunidad_id")
    )
    List<Comunidad> comunidades;
    @Column(name = "sugerencia_date")
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
