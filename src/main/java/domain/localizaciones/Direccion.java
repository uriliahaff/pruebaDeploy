package domain.localizaciones;

import domain.services.georef.entities.Localidad;
import domain.services.georef.entities.Municipio;
import domain.services.georef.entities.Provincia;

import javax.persistence.*;

@Entity (name = "direccion")
public class Direccion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Provincia provincia;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Municipio municipio;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Localidad localidad;

    public Provincia getProvincia() {
        return provincia;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public Direccion() {}
    public Direccion(Provincia provincia, Municipio municipio) {
        this.provincia = provincia;
        this.municipio = municipio;
    }

    public Direccion(Provincia provincia, Municipio municipio, Localidad localidad) {
        this.provincia = provincia;
        this.municipio = municipio;
        this.localidad = localidad;
    }

    public Direccion(Provincia provincia) {
        this.provincia = provincia;
    }
    public boolean sameLocation(Direccion direccion)
    {
        if (this.provincia == direccion.provincia)
        {
            if(this.municipio == null || direccion.municipio == null)
                return true;
            if (this.municipio== direccion.municipio)
            {
                if (this.localidad == null || direccion.localidad == null)
                    return true;
                if (this.localidad == direccion.localidad)
                    return true;
            }
        }
        return false;
    }
}
