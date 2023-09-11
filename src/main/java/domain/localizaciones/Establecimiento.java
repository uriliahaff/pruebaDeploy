package domain.localizaciones;

import domain.servicios.PrestacionDeServicio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/*
@Entity
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrestacionDeServicio> servicios = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;
    //private Entidad entidadEstablecimiento;

    public Direccion getDireccion() {
        return direccion;
    }


    //public Entidad getEntidad(){return entidadEstablecimiento;}

}*/
