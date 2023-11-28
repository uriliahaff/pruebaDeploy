package controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
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
import domain.services.NavBarVisualizer;
import domain.services.notificadorDeIncidentes.NotificadorDeIncidentes;
import domain.servicios.PrestacionDeServicio;
import io.javalin.http.Context;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IncidenteController {


    private RepositorioIncidente repositorioDeIncidentes;
    private RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    public IncidenteController(RepositorioIncidente repo, RepositorioUsuario repositorioUsuario){
        this.repositorioDeIncidentes = repo;
        this.repositorioUsuario = repositorioUsuario;
    }
/*
    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        List<Incidente> incidentes = this.repositorioDeIncidentes.findAll();
        model.put("incidentes", incidentes);
        model.put("username", context.cookie("username"));
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("layout_comun.hbs", model);
    }

    public void indexUser(Context context){
        Map<String, Object> model = new HashMap<>();
        List<Incidente> incidentes = this.repositorioDeIncidentes.findAllOpen();
        model.put("incidentes", incidentes);
        model.put("username", context.cookie("username"));
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("layout_comun.hbs", model);
    }*/
public void index(Context context){
    Map<String, Object> model = new HashMap<>();
    List<Incidente> incidentes = this.repositorioDeIncidentes.findAll();
    model.put("incidentes", incidentes);
    model.put("username", context.cookie("username"));
    Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
    NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
    model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));



    try {
        // Configura el loader para buscar plantillas en el directorio /templates
        Handlebars handlebars = new Handlebars().with(new ClassPathTemplateLoader("/templates", ".hbs"));

        // Compila el contenido del partial 'incidentes_template' y pásalo como 'body'
        Template template = handlebars.compile("incidente_template");
        String bodyContent = template.apply(model);
        model.put("body", bodyContent);
    } catch (IOException e) {
        e.printStackTrace();
        // Es importante manejar esta excepción adecuadamente
        context.status(500).result("Error al procesar la plantilla de incidentes.");
        return; // Sal del método aquí si no quieres procesar más el request debido al error
    }

    // Renderiza la plantilla común con el contenido incluido
    context.render("layout_comun.hbs", model);
}
public void indexUser(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<Incidente> incidentes = this.repositorioDeIncidentes.findAllOpen();
    model.put("incidentes", incidentes);
    model.put("username", context.cookie("username"));
    Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
    NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
    model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));


    try {
        // Configura el loader para buscar plantillas en el directorio /templates
        Handlebars handlebars = new Handlebars().with(new ClassPathTemplateLoader("/templates", ".hbs"));

        // Compila el contenido del partial 'incidentes_template' y pásalo como 'body'
        Template template = handlebars.compile("incidentesUser_template");
        String bodyContent = template.apply(model);
        model.put("body", bodyContent);
    } catch (IOException e) {
        e.printStackTrace();
        // Es importante manejar esta excepción adecuadamente
        context.status(500).result("Error al procesar la plantilla de incidentes.");
        return; // Sal del método aquí si no quieres procesar más el request debido al error
    }

    // Renderiza la plantilla común con el contenido incluido
    context.render("layout_comun.hbs", model);
}


    public void aperturaIncidentes(Context context){
        Map<String, Object> model = new HashMap<>();

        RepositorioServicio repositorioServicios = new RepositorioServicio();
        List<PrestacionDeServicio> prestacionDeServicios = repositorioServicios.buscarTodasLasPrestaciones();

        model.put("username", context.cookie("username"));
        model.put("prestaciones", prestacionDeServicios);
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("aperturaIncidente.hbs", model);
    }

    public void cerrarIncidente(Context context){

        Incidente incidente = repositorioDeIncidentes.findById(Integer.parseInt(context.pathParam("id")));

        LocalDateTime fechaActual = LocalDateTime.now();
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

        //DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //incidente.setFechaCierre(LocalDateTime.parse(fechaActual.format(formatoFecha)));
        Usuario usuarioInformante = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(usuarioInformante.getId());

        incidente.cerrarIncidente(fechaActual, miembro);
        this.repositorioDeIncidentes.update(incidente);
        NotificadorDeIncidentes.notificarIncidente(incidente);

        context.redirect("/incidentes");
    }

    public void revisionIncidente(Context context){
        Map<String, Object> model = new HashMap<>();
        model.put("username", context.cookie("username"));
        Incidente incidente = this.repositorioDeIncidentes.findById(Integer.parseInt(context.pathParam("id")));
        model.put("incidente", incidente);
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("revisionIncidente.hbs", model);
    }

    public void abrirIncidente(Context context){
        Incidente incidente = new Incidente();
        int userId = Integer.parseInt(context.cookie("id"));
        //Usuario usuarioInformante = repositorioUsuario.findUsuarioById());
        //List<Miembro> miembrosDelUsuario = repositorioUsuario.findMiembrosByUserId(usuarioInformante.getId());
        //Miembro miembro =  miembrosDelUsuario.get(0);
        Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(userId);
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
