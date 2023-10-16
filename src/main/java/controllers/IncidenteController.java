package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioIncidente;
import domain.Repositorios.RepositorioServicio;
import domain.Usuarios.EntidadPrestadora;
import domain.informes.Incidente;
import domain.servicios.PrestacionDeServicio;
import io.javalin.http.Context;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Incidente> incidentes = this.repositorioDeIncidentes.findAll();
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


}
