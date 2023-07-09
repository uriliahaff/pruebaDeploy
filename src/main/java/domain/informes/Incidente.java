package domain.informes;

import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.repositorios.RepositorioComunidades;
import domain.services.PrestacionDeServicio;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Incidente {
    private String descripcion;
    private Miembro miembroInformante;
    private PrestacionDeServicio servicioAfectado;

    private LocalDate fechaInicio;

    private LocalDate fechaCierre;

    //TODO: Cambiar en el filtro la localizacion
    public Incidente(String descripcion, Miembro miembroInformante, PrestacionDeServicio servicioAfectado, LocalDate fechaInicio) {
        this.descripcion = descripcion;
        this.miembroInformante = miembroInformante;
        this.servicioAfectado = servicioAfectado;
        this.fechaInicio = fechaInicio;

        List<Comunidad> comunidades = RepositorioComunidades.getInstance().obtenerComunidades();

        List<Miembro> miembrosUnicos = comunidades.stream()
                .filter(comunidad -> comunidad.deInteres(servicioAfectado.getServicio()))
                .flatMap(comunidad -> comunidad.miembrosFiltradosPorInteresEnLocalizacion(servicioAfectado.getLocalizacion()).stream())
                .distinct()
                .collect(Collectors.toList());
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

}
