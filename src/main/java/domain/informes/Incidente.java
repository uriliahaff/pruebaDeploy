package domain.informes;

import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.other.EntityManagerProvider;
import domain.services.notificadorDeIncidentes.NotificadorDeIncidentes;
import domain.servicios.PrestacionDeServicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "incidente")
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Column(name = "descripcion",nullable = false)
    @Getter
    @Setter
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "miembro_informante_id", nullable = false)
    @Getter
    @Setter
    private Miembro miembroInformante;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "miembro_analizador_id")
    @Getter
    @Setter
    private Miembro miembroAnalizador;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    @Getter
    @Setter
    private PrestacionDeServicio servicioAfectado;

    @ManyToMany
    @JoinTable(
            name = "incidente_comunidad",
            joinColumns = @JoinColumn(name = "incidente_id"),
            inverseJoinColumns = @JoinColumn(name = "comunidad_id")
    )
    @Getter
    @Setter
    private List<Comunidad> comunidadesAfectadas = new ArrayList<>();

    public void addComunidadesAfectadas(List<Comunidad> comunidadesAfectadas)
    {
        this.comunidadesAfectadas.addAll(comunidadesAfectadas);
    }
    @Column(name = "fechaInicio",nullable = false)
    @Getter
    @Setter
    private LocalDateTime fechaInicio;

    @Column(name = "fechaCierre")
    @Getter
    @Setter
    private LocalDateTime fechaCierre;

    // Getters y Setters
    public List<Comunidad> getComunidadesAfectadas() {
        return comunidadesAfectadas;
    }
    public Incidente()
    {}
    //TODO: Cambiar en el filtro la localizacion
    public Incidente(String descripcion, Miembro miembroInformante, PrestacionDeServicio servicioAfectado, LocalDateTime fechaInicio) {
        this.descripcion = descripcion;
        this.miembroInformante = miembroInformante;
        getComunidadesAfectadas().addAll(miembroInformante.getComunidades());
        this.servicioAfectado = servicioAfectado;
        this.fechaInicio = fechaInicio;


        //TODO: Aca hay que generar la notificacion para cada miembro
    }
    public Miembro getMiembroAnalizador() {
        return miembroAnalizador;
    }


    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Miembro getMiembroInformante() {
        return miembroInformante;
    }

    public PrestacionDeServicio getServicioAfectado() {
        return servicioAfectado;
    }

    public void cerrarIncidente(LocalDateTime date, Miembro miembroAnalizador)
    {
        this.fechaCierre=date;
        this.miembroAnalizador = miembroAnalizador;
    }

}
