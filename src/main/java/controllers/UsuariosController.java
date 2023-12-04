package controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import io.javalin.http.Context;

import java.io.IOException;
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
        model.put("editarUsuario", user.tienePermiso("editarUsuario"));
        System.out.println("\n" + user.tienePermiso("editarUsuario"));
        model.put("editarRoles", user.tienePermiso("editarRoles"));
        try {
            // Configura el loader para buscar plantillas en el directorio /templates
            Handlebars handlebars = new Handlebars().with(new ClassPathTemplateLoader("/templates", ".hbs"));

            // Compila el contenido del partial 'incidentes_template' y pásalo como 'body'
            Template template = handlebars.compile("usuarios_template");
            String bodyContent = template.apply(model);
            model.put("body", bodyContent);
            context.render("usuarios.hbs", model);
        } catch (IOException e) {
            e.printStackTrace();
            // Es importante manejar esta excepción adecuadamente
            context.status(500).result("Error al procesar la plantilla de incidentes.");
            return; // Sal del método aquí si no quieres procesar más el request debido al error
        }

        context.render("layout_comun.hbs", model);
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
