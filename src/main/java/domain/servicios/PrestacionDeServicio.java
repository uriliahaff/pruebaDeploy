package domain.servicios;

import domain.entidades.Establecimiento;
import domain.localizaciones.Direccion;
//import domain.localizaciones.Establecimiento;



import javax.persistence.*;

@Entity
@Table(name = "prestacion_de_servicio")
public class PrestacionDeServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = false)
    private Establecimiento establecimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    public PrestacionDeServicio(){}
    public PrestacionDeServicio(Servicio servicio, Establecimiento establecimiento, Estado estado) {
        this.servicio = servicio;
        this.establecimiento = establecimiento;
        this.estado = estado;
    }
    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Direccion getLocalizacion() {
        return establecimiento.getDireccion();
    }
}


