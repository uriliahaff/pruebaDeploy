package controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import domain.Repositorios.RepositorioComunidad;
import domain.Repositorios.RepositorioServicio;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import domain.servicios.Servicio;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComunidadController {
    private static RepositorioComunidad repositorioComunidad = new RepositorioComunidad();
    private static RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    private static RepositorioServicio repositorioServicio = new RepositorioServicio();

    public void indexComunidades(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("username", context.cookie("username"));
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        model.put("UserId",context.cookie("id"));

        CommonController.fillNav(model, user);

        List<Comunidad> comunidades = repositorioComunidad.findAll();
        model.put("comunidades", comunidades);
        model.put("editarComunidad", user.tienePermiso("editarComunidad")); // RolX es el rol necesario

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
        Usuario user = repositorioUsuario.findUsuarioById(userId);
        model.put("username", context.cookie("username"));
        model.put("editarComunidad", user.tienePermiso("editarComunidad") || comunidad.hasAdmin(userId));
        model.put("esAdmin", comunidad.hasAdmin(userId));

        boolean esTipoMiembro = repositorioUsuario.findMiembroByUsuarioId(userId) != null;
        model.put("usuarioPerteneceAComunidad", comunidad.esMiembroByUserId(userId));
        model.put("usuarioEsTipoMiembro", esTipoMiembro);
        model.put("puedeUnirse", !comunidad.esMiembroByUserId(userId) && esTipoMiembro);
        model.put("comunidad", comunidad);

        List<Map<String, Object>> listaDeMiembros = new ArrayList<>();


        for (Miembro miembro : comunidad.getMiembros()) {
            Map<String, Object> miembroMap = new HashMap<>();
            miembroMap.put("nombre", miembro.getNombre());
            miembroMap.put("id", miembro.getId());
            miembroMap.put("correoElectronico", miembro.getCorreoElectronico());
            miembroMap.put("telefono", miembro.getTelefono());

            boolean esAdmin = comunidad.hasAdmin(miembro.getUsuario().getId());
            miembroMap.put("MiembroNoEsAdmin", !esAdmin); // El opuesto de esAdmin


            listaDeMiembros.add(miembroMap);
        }


        model.put("listaMiembros",listaDeMiembros);

        model.put("listaDeServicios", repositorioServicio.findAll().stream()
                .filter(servicio -> !comunidad.deInteres(servicio))
                .collect(Collectors.toList()));

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
    public void removerAdmin(Context context) {
        int comunidadId = Integer.parseInt(context.formParam("comunidadId"));
        int adminId = Integer.parseInt(context.formParam("adminId"));

        Comunidad comunidad = repositorioComunidad.find(comunidadId);

        comunidad.removerAdmin(adminId);

        repositorioComunidad.update(comunidad);

        context.redirect("/comunidad/" + comunidadId);
    }

    public void joinComunidad(Context context)
    {
        Map<String, Object> model = new HashMap<>();
        int comunidadId = Integer.parseInt(context.pathParam("comunidadId"));
        int userId = Integer.parseInt(context.cookie("id"));

        Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(userId);
        Comunidad comunidad = repositorioComunidad.find(comunidadId);

        miembro.addComunidad(comunidad);
        comunidad.agregarMiembros(miembro);

        repositorioComunidad.update(comunidad);
        repositorioUsuario.updateMiembro(miembro);


        context.redirect("/comunidad/" + comunidadId);

    }

    public void addInteres(Context context)
    {
        Map<String, Object> model = new HashMap<>();

        Map<String, List<String>> parametros = context.formParamMap();

        for (Map.Entry<String, List<String>> entry : parametros.entrySet()) {
            System.out.println("Clave: " + entry.getKey() + ", Valor: " + entry.getValue());
        }
        int comunidadId = Integer.parseInt(context.formParam("comunidadId"));
        int servicioId = Integer.parseInt(context.formParam("servicioSeleccionado"));

        Comunidad comunidad = repositorioComunidad.find(comunidadId);
        Servicio servicio = repositorioServicio.findServicioById(servicioId);

        comunidad.agregarInteres(servicio);
        repositorioComunidad.update(comunidad);

        context.redirect("/comunidad/" + comunidadId);

    }
    public void removerInteres( Context context)
    {
        int comunidadId = Integer.parseInt(context.formParam("comunidadId"));
        int servicioId = Integer.parseInt(context.formParam("interesId"));

        Comunidad comunidad = repositorioComunidad.find(comunidadId);
        //Servicio servicio = repositorioServicio.findServicioById(servicioId);

        comunidad.removerInteres(servicioId);
        repositorioComunidad.update(comunidad);
        context.redirect("/comunidad/" + comunidadId);

    }


}




