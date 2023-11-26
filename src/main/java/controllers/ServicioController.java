package controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import domain.Repositorios.RepositorioComunidad;
import domain.Repositorios.RepositorioServicio;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import domain.servicios.Servicio;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioController
{
    private static RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    private static RepositorioServicio repositorioServicio = new RepositorioServicio();

    public void indexServicios(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("username", context.cookie("username"));
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));

        if(!user.usuarioTieneRol("admin"))
            context.redirect("/");
        CommonController.fillNav(model,user);
        try {
            // Configura el loader para buscar plantillas en el directorio /templates
            Handlebars handlebars = new Handlebars().with(new ClassPathTemplateLoader("/templates", ".hbs"));

            // Compila el contenido del partial 'incidentes_template' y pásalo como 'body'
            model.put("servicios",repositorioServicio.findAll());
            Template template = handlebars.compile("servicios");
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

    public void crearServicio(Context context)
    {
        String nombre = context.formParam("nombre");
        String descripcion = context.formParam("descripcion");

        repositorioServicio.save(new Servicio(nombre,descripcion));
        context.redirect("/servicios");
    }


}
