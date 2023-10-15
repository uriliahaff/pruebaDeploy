package domain.services.service1.model;

import domain.Usuarios.Comunidades.Comunidad;
import domain.services.service1.PropuestaDeFusion;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PropuestaDeFusionModel implements Serializable
{
    public List<ComunidadModel> comunidadesAFusionar;
    public LocalDateTime fechaPropuesta;

    public PropuestaDeFusionModel(){}

    public List<ComunidadModel> getComunidadesAFusionar() {
        return comunidadesAFusionar;
    }
    public LocalDateTime getFechaPropuesta() {
        return fechaPropuesta;
    }

    public PropuestaDeFusionModel(List<Comunidad> comunidadesAFusionar, LocalDateTime fechaPropuesta) {
        this.comunidadesAFusionar =  comunidadesAFusionar.stream()
                .map(ComunidadModel::new)
                .collect(Collectors.toList());
        this.fechaPropuesta = fechaPropuesta;
    }
    public PropuestaDeFusionModel(PropuestaDeFusion propuestaDeFusion)
    {
        this(propuestaDeFusion.getComunidades(),propuestaDeFusion.getSugerenciaDate());
    }
}
