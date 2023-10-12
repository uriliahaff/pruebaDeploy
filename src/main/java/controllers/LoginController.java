package controllers;

import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Usuario;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginController {

    private RepositorioUsuario repositorioDeUsuario;

    public LoginController(RepositorioUsuario repo){
        this.repositorioDeUsuario = repo;
    }
    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        //List<Usuario> usuarios = this.repositorioDeUsuario.buscarTodos();
        //model.put("usuarios", usuarios);

        context.render("login.hbs");
    }

    public void loginAttempt(Context context){
        Usuario usuario = repositorioDeUsuario.findUsuarioByUsername(context.formParam("username"));
        String username = context.formParam("username");
        String password = context.formParam("password");
        System.out.println("Nombre de usuario: " + username);
        System.out.println("Contraseña: " + password);

        if(usuario != null){
            if(usuario.getPassword() == context.formParam("password")){
                context.render("correcto.hbs");
            }
        } else{
            context.render("incorrecto.hbs");

        }



    }

}
