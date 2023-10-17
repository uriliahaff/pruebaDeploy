package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingController {
    private RepositorioUsuario repositorioUsuario;


    public RankingController(RepositorioUsuario repoUsuario){
        this.repositorioUsuario = repoUsuario;
    }

    public void index(Context context){
        Map<String, Object> model = new HashMap<>();

        model.put("username", context.cookie("username"));
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("rankings.hbs", model);
    }

    public void ranking(Context context){
        Map<String, Object> model = new HashMap<>();

        model.put("username", context.cookie("username"));
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("ranking.hbs", model);
    }



}
