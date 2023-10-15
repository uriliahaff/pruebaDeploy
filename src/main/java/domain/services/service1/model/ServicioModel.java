package domain.services.service1.model;

import domain.Usuarios.Comunidades.Miembro;
import domain.servicios.Servicio;

import java.io.Serializable;

public class ServicioModel implements Serializable
{
    public Long id;
    public String nombre;

    public ServicioModel(){}
    public ServicioModel(Servicio servicio)
    {
        this.id = (long) servicio.getId();
        this.nombre = servicio.getNombre();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
