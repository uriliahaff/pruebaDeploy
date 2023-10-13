package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntidadesOrganismosController {

    private RepositorioEntidadPrestadoraOrganismoControl repositorioDeEntidadesPrestadoras;


    public EntidadesOrganismosController(RepositorioEntidadPrestadoraOrganismoControl repo){
        this.repositorioDeEntidadesPrestadoras = repo;
    }
    public void indexEntidades(Context context){
        Map<String, Object> model = new HashMap<>();
        List<EntidadPrestadora> entidades = this.repositorioDeEntidadesPrestadoras.buscarTodosEntidades();
        model.put("entidades", entidades);

        context.render("cargaEntidades.hbs", model);
    }

    public void indexOrganismos(Context context){
        Map<String, Object> model = new HashMap<>();
        List<OrganismoDeControl> organismos = this.repositorioDeEntidadesPrestadoras.buscarTodosOrganismos();
        model.put("organismos", organismos);

        context.render("cargaOrganismos.hbs", model);
    }
}
