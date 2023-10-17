package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import io.javalin.http.Context;

import java.util.*;

public class UsuariosController {

    RepositorioUsuario repositorioDeUsuarios;

    public UsuariosController (RepositorioUsuario repo){
        this.repositorioDeUsuarios = repo;
    }

    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        List<Usuario> usuarios = this.repositorioDeUsuarios.buscarTodosUsuarios();
        model.put("usuarios", usuarios);
        model.put("username", context.cookie("username"));
        Usuario user = repositorioDeUsuarios.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("usuarios.hbs", model);
    }

    public void editar(Context context){
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = this.repositorioDeUsuarios.findUsuarioById(Integer.parseInt(context.pathParam("id")));
        List<Rol> rolesUsuario = usuario.getRoles();
        List<Rol> roles = this.repositorioDeUsuarios.buscarTodosRoles();

        for (Rol rolUsuario : rolesUsuario) {
            roles.removeIf(rol -> rol.getId() == rolUsuario.getId());
        }

        model.put("usuario", usuario);
        model.put("rolesUsados", rolesUsuario);
        model.put("rolesSinUsar", roles);
        model.put("username", context.cookie("username"));
        Usuario user = repositorioDeUsuarios.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
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
        List<Rol> rolesSeleccionados = new ArrayList<>();
        for (Rol rol : this.repositorioDeUsuarios.buscarTodosRoles()) {
            String checkboxParam = context.formParam(Integer.toString(rol.getId()));
            if (checkboxParam != null) {
                rolesSeleccionados.add(rol);
            }
        }
        usuario.setRoles(rolesSeleccionados);

    }

}
