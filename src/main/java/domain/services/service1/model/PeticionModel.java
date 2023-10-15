package domain.services.service1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeticionModel implements Serializable
{
    public List<PropuestaDeFusionModel> propuestas;
    public List<ComunidadModel> comunidades;

    public PeticionModel()
    {}
    public PeticionModel(List<PropuestaDeFusionModel> propuestas, List<ComunidadModel> comunidades)
    {
        this.propuestas = propuestas;
        this.comunidades = comunidades;
    }
}
