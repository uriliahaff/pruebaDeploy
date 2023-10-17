package controllers;

import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import io.javalin.http.Context;
import io.javalin.security.RouteRole;

import java.util.*;

public class LoginController {

    private RepositorioUsuario repositorioDeUsuario;

    public LoginController(RepositorioUsuario repo){
        this.repositorioDeUsuario = repo;
    }
    public void index(Context context){
        Map<String, Object> model = new HashMap<>();

        context.render("login.hbs");
    }

    public void admin(Context context){
        Map<String, Object> model = new HashMap<>();
        System.out.println("Username: " + context.attribute("username"));

        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        Usuario user = repoUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        model.put("username", context.cookie("username"));

        context.render("dashboard.hbs", model);
    }

    public void loginAttempt(Context context){
        Usuario usuario = repositorioDeUsuario.findUsuarioByUsername(context.formParam("username"));
        String password = context.formParam("password");
        //TODO HASHING PASSWORD
        if(usuario != null){
            if(usuario.getPassword().equals(password)){
                context.redirect("/");
                context.cookie("id", String.valueOf(usuario.getId()));
                context.cookie("username", usuario.getUsername());
            }
            else{
                context.redirect("/login");
            }
        } else{
            context.redirect("/login");

        }
    }

    public void logout(Context context){
        context.cookie("id","",0);
        context.cookie("username","",0);

        context.redirect("/login");
    }



}
