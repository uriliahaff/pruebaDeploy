package domain.informes;

import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.other.EntityManagerProvider;
import domain.services.notificadorDeIncidentes.NotificadorDeIncidentes;
import domain.servicios.PrestacionDeServicio;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "incidente")
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembroInformante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private PrestacionDeServicio servicioAfectado;

    @ManyToMany
    @JoinTable(
            name = "incidente_comunidad",
            joinColumns = @JoinColumn(name = "incidente_id"),
            inverseJoinColumns = @JoinColumn(name = "comunidad_id")
    )
    private List<Comunidad> comunidadesAfectadas = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaCierre;

    // Getters y Setters
    public List<Comunidad> getComunidadesAfectadas() {
        return comunidadesAfectadas;
    }
    public Incidente()
    {}
    //TODO: Cambiar en el filtro la localizacion
    public Incidente(String descripcion, Miembro miembroInformante, PrestacionDeServicio servicioAfectado, LocalDate fechaInicio) {
        this.descripcion = descripcion;
        this.miembroInformante = miembroInformante;
        getComunidadesAfectadas().addAll(miembroInformante.getComunidades());
        this.servicioAfectado = servicioAfectado;
        this.fechaInicio = fechaInicio;

        this.save();
        //HistorialIncidentes.getInstance().agregarIncidente(this);
        NotificadorDeIncidentes.notificarIncidente(this);
        //TODO: Aca hay que generar la notificacion para cada miembro
        //miembrosUnicos.forEach(miembro -> );
    }


    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaCierre() {
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

    public void cerrarIncidente(LocalDate date)
    {
        fechaCierre=date;
        this.update();
    }
    public void update() {
        EntityManager em = EntityManagerProvider.getInstance().getEntityManager();
        em.getTransaction().begin();
        em.merge(this);
        em.getTransaction().commit();
    }

    public void save() {
        EntityManager em = EntityManagerProvider.getInstance().getEntityManager();
        em.getTransaction().begin();
        em.persist(this);
        em.getTransaction().commit();
    }
}
