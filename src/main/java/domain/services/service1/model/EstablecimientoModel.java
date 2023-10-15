package domain.services.service1.model;

import domain.Usuarios.Comunidades.Miembro;
import domain.entidades.Establecimiento;

import java.io.Serializable;

public class EstablecimientoModel implements Serializable
{
    public Long id;
    public String nombre;
    public EstablecimientoModel(){}
    public EstablecimientoModel(Establecimiento establecimiento)
    {
        this.id = (long) establecimiento.getId();
        this.nombre = establecimiento.getNombre();
    }
    public EstablecimientoModel(Long id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
