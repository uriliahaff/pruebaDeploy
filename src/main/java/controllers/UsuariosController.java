package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.Usuario;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UsuariosController {

    RepositorioUsuario repositorioDeUsuarios;

    public UsuariosController (RepositorioUsuario repo){
        this.repositorioDeUsuarios = repo;
    }

    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        List<Usuario> usuarios = this.repositorioDeUsuarios.buscarTodosUsuarios();
        model.put("usuarios", usuarios);
        model.put("username", context.attribute("username"));

        context.render("usuarios.hbs", model);
    }

    public void editar(Context context){
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = this.repositorioDeUsuarios.findUsuarioById(Integer.parseInt(context.pathParam("id")));
        model.put("usuario", usuario);
        model.put("username", context.attribute("username"));

        context.render("editarUsuario.hbs", model);
    }

    public void update (Context context){
        Usuario usuario = this.repositorioDeUsuarios.findUsuarioById(Integer.parseInt(context.pathParam("id")));
        this.asignarParametros(usuario, context);
        this.repositorioDeUsuarios.actualizar(usuario);
        context.redirect("/usuarios");
    }

    public void delete(Context context) {
        Usuario usuario = this.repositorioDeUsuarios.findUsuarioById(Integer.parseInt(context.pathParam("id")));
        this.repositorioDeUsuarios.delete(usuario);
        context.redirect("/usuarios");
    }

    private void asignarParametros(Usuario usuario, Context context) {

        if(!Objects.equals(context.formParam("username"), "")) {
            usuario.setUsername(context.formParam("username"));
        }
        if(!Objects.equals(context.formParam("password"), "")) {
            usuario.setPassword(context.formParam("password"));
        }
    }

}
