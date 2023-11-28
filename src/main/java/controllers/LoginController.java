package controllers;

import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import io.javalin.http.Context;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LoginController {

    private RepositorioUsuario repositorioDeUsuario;

    public LoginController(RepositorioUsuario repo){
        this.repositorioDeUsuario = repo;
    }
    public void index(Context context){

        context.render("login.hbs");
    }

    public void registro(Context context){

        context.render("signin.hbs");
    }

    public void admin(Context context){
        Map<String, Object> model = new HashMap<>();
        System.out.println("Username: " + context.attribute("username"));

        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        Usuario user = repoUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        System.out.println(user);
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        model.put("username", context.cookie("username"));
        model.put("id", user.getId());

        context.render("dashboard.hbs", model);
    }

    public void loginAttempt(@NotNull Context context){
        //Usuario usuario = repositorioDeUsuario.findUsuarioByUsername(context.formParam("username"));
        String username = context.formParam("username");
        String password = context.formParam("password");

        Usuario usuario = repositorioDeUsuario.findUsuarioByUsernameAndPassword(username, password);

        if(usuario != null){
            //if(usuario.getPassword().equals(password)){
                context.redirect("/");
                context.cookie("id", String.valueOf(usuario.getId()));
                context.cookie("username", usuario.getUsername());
           /* }
            else{
                context.redirect("/login");
            }*/
        } else{
            context.redirect("/login");

        }
    }

    public void signinAttempt(Context context){

    }

    public void logout(Context context){
        context.cookie("id","",0);
        context.cookie("username","",0);

        context.redirect("/login");
    }



}
