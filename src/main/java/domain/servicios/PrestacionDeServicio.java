package domain.servicios;

import domain.entidades.Establecimiento;
import domain.localizaciones.Direccion;
//import domain.localizaciones.Establecimiento;



import javax.persistence.*;

@Entity
public class PrestacionDeServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Establecimiento establecimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


