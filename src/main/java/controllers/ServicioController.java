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

        model.put("servicios",repositorioServicio.findAll());
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        navBarVisualizer.colocarItems(user.getRoles(), model);

        context.render("servicios.hbs", model);
    }

    public void crearServicio(Context context)
    {
        String nombre = context.formParam("nombre");
        String descripcion = context.formParam("descripcion");

        repositorioServicio.save(new Servicio(nombre,descripcion));
        context.redirect("/admin/servicios");
    }


}
