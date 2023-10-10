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

    public LoginController(){

    }
    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        //List<Usuario> usuarios = this.repositorioDeUsuario.buscarTodos();
        //model.put("usuarios", usuarios);

        context.render("login.hbs");
    }

}
