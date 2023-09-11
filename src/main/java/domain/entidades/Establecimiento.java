package domain.entidades;

import domain.localizaciones.Direccion;
import domain.services.georef.entities.Localidad;
import domain.services.georef.entities.Municipio;
import domain.services.georef.entities.Provincia;
import domain.servicios.PrestacionDeServicio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "establecimiento")
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @Column(nullable = false)
    private String nombre;
    @Column
    private String descripcion;

    @Transient
    private List<PrestacionDeServicio> servicios;

    @ManyToOne
    private Entidad entidad;

    @OneToOne(cascade = CascadeType.ALL)
    private Direccion direccion;
    public Establecimiento(String nombre,String descripcion,Provincia provincia,Localidad localidad,Municipio municipio){
        this.nombre= nombre;
        this.descripcion=descripcion;

        this.direccion = new Direccion(provincia, municipio, localidad);
        this.servicios = new ArrayList<>();
    }public Establecimiento(String nombre,String descripcion, Direccion direccion){
        this.nombre= nombre;
        this.descripcion=descripcion;

        this.direccion = direccion;
        this.servicios = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void agregarServicio(PrestacionDeServicio servicio) {
        servicios.add(servicio); // Agregar un servicio a la lista de servicios
    }
}
