package domain.services.service1.model;

import domain.Usuarios.Comunidades.Miembro;

import java.io.Serializable;

public class MiembroModel implements Serializable
{
    public Long id;
    public String nombre;

    public MiembroModel(){}
    public MiembroModel(Miembro miembro)
    {
        //System.out.println("Nombre: "+ miembro.getNombre() + "   Id: "+miembro.getId());
        this.id = (long) miembro.getId();
        this.nombre = miembro.getNombre();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
