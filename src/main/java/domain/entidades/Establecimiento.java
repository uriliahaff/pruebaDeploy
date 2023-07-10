package domain.entidades;

import domain.services.georef.entities.Localidad;
import domain.services.georef.entities.Municipio;
import domain.services.georef.entities.Provincia;
import domain.servicios.PrestacionDeServicio;

import java.util.ArrayList;
import java.util.List;

public class Establecimiento {
    private String nombre;
    private String descripcion;
    private List<PrestacionDeServicio> servicios;
    private Provincia provincia;
    private Localidad localidad;
    private Municipio municipio;
    public Establecimiento(String nombre,String descripcion,Provincia provincia,Localidad localidad,Municipio municipio){
        this.nombre= nombre;
        this.descripcion=descripcion;
        this.provincia=provincia;
        this.localidad=localidad;
        this.municipio=municipio;
        this.servicios = new ArrayList<>();
    }
    public void agregarServicio(PrestacionDeServicio servicio) {
        servicios.add(servicio); // Agregar un servicio a la lista de servicios
    }
}
