package controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import domain.Repositorios.RepositorioRol;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Permiso;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolController
{
    private RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    private RepositorioRol repositorioRol = new RepositorioRol();
    public void indexRols(Context context)
    {
        Map<String,Object> model = new HashMap<>();
        model.put("UserId",context.cookie("id"));
        List<Rol> roles = repositorioRol.findAllRoles();
        roles.size();
        roles.forEach(rol -> rol.getPermisos().size());
        List<Permiso> permisos = repositorioRol.findAllPermisos();

        roles.forEach(rol -> rol.getPermisos().forEach(permiso -> permiso.getDescripcion()));
        model.put("roles",roles);

        model.put("permisosDisponibles",permisos);

        model.put("UserId", context.cookie("id"));


        try {
            // Configura el loader para buscar plantillas en el directorio /templates
            Handlebars handlebars = new Handlebars().with(new ClassPathTemplateLoader("/templates", ".hbs"));

            // Compila el contenido del partial 'incidentes_template' y pásalo como 'body'
            Template template = handlebars.compile("roles");
            String bodyContent = template.apply(model);
            model.put("body", bodyContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Es importante manejar esta excepción adecuadamente
            context.status(500).result("Error al procesar la plantilla de incidentes.");
            return; // Sal del método aquí si no quieres procesar más el request debido al error
        }

        // Renderiza la plantilla común con el contenido incluido
        context.render("layout_comun.hbs", model);
    }

    public void addPermiso(Context context)
    {
        int rolId = Integer.parseInt(context.pathParam("id"));
        int permisoId = Integer.parseInt(context.formParam("permisoId"));

        System.out.println(rolId + "\n" + permisoId);
        Rol rol = repositorioRol.findRolById(rolId);
        Permiso permiso = repositorioRol.findPermisoById(permisoId);
        rol.getPermisos().add(permiso);
        repositorioRol.update(rol);
        context.redirect("/roles");

    }
    public void borrarPermiso(Context context)
    {
        int rolId = Integer.parseInt(context.pathParam("id"));
        int permisoId = Integer.parseInt(context.pathParam("permisoId"));

        Rol rol = repositorioRol.findRolById(rolId);
        Permiso permiso = repositorioRol.findPermisoById(permisoId);
        rol.getPermisos().remove(permiso);
        repositorioRol.update(rol);
        context.redirect("/roles");

    }

    public void crearRol(Context context)
    {
        String rolName = context.formParam("nuevoRolNombre");
        if(rolName!=null && !rolName.isEmpty())
            repositorioRol.save(new Rol(rolName, new ArrayList<>()));
        context.redirect("/roles");
    }
}
