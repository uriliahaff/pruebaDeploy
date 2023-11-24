package controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import domain.Repositorios.RepositorioComunidad;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComunidadController {
    private static RepositorioComunidad repositorioComunidad = new RepositorioComunidad();
    private static RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    public void indexComunidades(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("username", context.cookie("username"));
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));

        List<Comunidad> comunidades = repositorioComunidad.findAll();
        model.put("comunidades", comunidades);
        model.put("usuarioPuedeEliminar", user.getRoles()); // RolX es el rol necesario

        try {
            // Configura el loader para buscar plantillas en el directorio /templates
            Handlebars handlebars = new Handlebars().with(new ClassPathTemplateLoader("/templates", ".hbs"));

            // Compila el contenido del partial 'incidentes_template' y pásalo como 'body'
            Template template = handlebars.compile("Comunidades");
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

    public void eliminarComunidad(Context context) {
        int comunidadId = Integer.parseInt(context.pathParam("id"));
        repositorioComunidad.deleteById(comunidadId);
        context.redirect("/comunidades");
    }

    public void mostrarComunidad(Context context) {
        Map<String, Object> model = new HashMap<>();
        int comunidadId = Integer.parseInt(context.pathParam("id"));
        Comunidad comunidad = repositorioComunidad.find(comunidadId);

        if (comunidad == null) {
            context.status(404).result("Comunidad no encontrada");
            return;
        }

        int userId = Integer.parseInt(context.cookie("id"));
        model.put("comunidad", comunidad);
        Usuario user = repositorioUsuario.findUsuarioById(userId);
        model.put("username", context.cookie("username"));
        model.put("esAdmin", comunidad.hasAdmin(userId) || user.usuarioTieneRol("admin"));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));


        try {
            // Configura el loader para buscar plantillas en el directorio /templates
            Handlebars handlebars = new Handlebars().with(new ClassPathTemplateLoader("/templates", ".hbs"));

            // Compila el contenido del partial 'detalle_comunidad' y pásalo como 'body'
            Template template = handlebars.compile("comunidad_detalle");
            String bodyContent = template.apply(model);
            model.put("body", bodyContent);
        } catch (IOException e) {
            e.printStackTrace();
            context.status(500).result("Error al procesar la plantilla.");
            return;
        }

        // Renderiza la plantilla común con el contenido incluido
        context.render("layout_comun.hbs", model);
    }


    public void expulsarMiembro(Context context) {
        int comunidadId = Integer.parseInt(context.pathParam("comunidadId"));
        int miembroId = Integer.parseInt(context.pathParam("miembroId"));

        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        Comunidad comunidad = repositorioComunidad.find(comunidadId);

        // Verifica si el usuario es administrador de la comunidad
        if (!comunidad.getAdmins().contains(user) && !user.usuarioTieneRol("admin")) {
            context.status(403).result("No autorizado");
            return;
        }

        Miembro miembro = comunidad.getMiembro(miembroId);
        comunidad.removeMiembro(miembro);
        miembro.removeComunidad(comunidad);
        repositorioUsuario.updateMiembro(miembro);
        repositorioComunidad.update(comunidad);

        context.redirect("/comunidad/" + comunidadId);
    }


    public void ascenderAAdmin(Context context) {
            int comunidadId = Integer.parseInt(context.pathParam("comunidadId"));
            int miembroId = Integer.parseInt(context.pathParam("miembroId"));

            Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
            Comunidad comunidad = repositorioComunidad.find(comunidadId);

            if (!comunidad.getAdmins().contains(user) && !user.usuarioTieneRol("admin")) {
                context.status(403).result("No autorizado");
                return;
            }
            Miembro miembro = comunidad.getMiembro(miembroId);
            comunidad.addAdmins(miembro.getUsuario());
            repositorioComunidad.update(comunidad);

            context.redirect("/comunidad/" + comunidadId);
        }
    }




