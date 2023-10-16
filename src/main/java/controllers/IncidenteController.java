package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioIncidente;
import domain.Repositorios.RepositorioServicio;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import domain.informes.Incidente;
import domain.services.notificadorDeIncidentes.NotificadorDeIncidentes;
import domain.servicios.PrestacionDeServicio;
import io.javalin.http.Context;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IncidenteController {


    private RepositorioIncidente repositorioDeIncidentes;


    public IncidenteController(RepositorioIncidente repo){
        this.repositorioDeIncidentes = repo;
    }

    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        List<Incidente> incidentes = this.repositorioDeIncidentes.findAll();
        model.put("incidentes", incidentes);
        model.put("username", context.cookie("username"));

        context.render("incidentes.hbs", model);
    }

    public void indexUser(Context context){
        Map<String, Object> model = new HashMap<>();
        List<Incidente> incidentes = this.repositorioDeIncidentes.findAllOpen();
        model.put("incidentes", incidentes);
        model.put("username", context.cookie("username"));

        context.render("incidentesUser.hbs", model);
    }

    public void aperturaIncidentes(Context context){
        Map<String, Object> model = new HashMap<>();

        RepositorioServicio repositorioServicios = new RepositorioServicio();
        List<PrestacionDeServicio> prestacionDeServicios = repositorioServicios.buscarTodasLasPrestaciones();

        model.put("username", context.cookie("username"));
        model.put("prestaciones", prestacionDeServicios);

        context.render("aperturaIncidente.hbs", model);
    }

    public void cerrarIncidente(Context context){

        Incidente incidente = repositorioDeIncidentes.findById(Integer.parseInt(context.pathParam("id")));

        LocalDateTime fechaActual = LocalDateTime.now();
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

        //DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //incidente.setFechaCierre(LocalDateTime.parse(fechaActual.format(formatoFecha)));
        Usuario usuarioInformante = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        List<Miembro> miembrosDelUsuario = repositorioUsuario.findMiembrosByUserId(usuarioInformante.getId());
        Miembro miembro =  miembrosDelUsuario.get(0);
        incidente.cerrarIncidente(fechaActual, miembro);
        this.repositorioDeIncidentes.update(incidente);
        NotificadorDeIncidentes.notificarIncidente(incidente);

        context.redirect("/incidentes");
    }

    public void abrirIncidente(Context context){
        Incidente incidente = new Incidente();
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
        Usuario usuarioInformante = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        List<Miembro> miembrosDelUsuario = repositorioUsuario.findMiembrosByUserId(usuarioInformante.getId());
        Miembro miembro =  miembrosDelUsuario.get(0);
        incidente.setMiembroInformante(miembro);
        this.asignarParametros(incidente, context);

        LocalDateTime fechaActual = LocalDateTime.now();
        //DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //incidente.setFechaInicio(LocalDateTime.parse(fechaActual.format(formatoFecha)));
        incidente.setFechaInicio(fechaActual);

       List<Comunidad> comunidadesAfectadas = miembro.getComunidades();
        incidente.addComunidadesAfectadas(comunidadesAfectadas);
        this.repositorioDeIncidentes.save(incidente);
        NotificadorDeIncidentes.notificarIncidente(incidente);

        context.redirect("/incidentes");

    }

    private void asignarParametros(Incidente incidente, Context context) {

        if(!Objects.equals(context.formParam("descripcion"), "")) {
            incidente.setDescripcion(context.formParam("descripcion"));
        }
        if(!Objects.equals(context.formParam("prestacion"), "")) {
            RepositorioServicio repositorioServicio = new RepositorioServicio();
            PrestacionDeServicio prestacion = repositorioServicio.findPrestacionById(Integer.parseInt(context.formParam("prestacion")));
            incidente.setServicioAfectado(prestacion);
        }


    }



}
