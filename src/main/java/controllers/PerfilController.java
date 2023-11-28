package controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import domain.Repositorios.RepositorioDireccion;
import domain.Repositorios.RepositorioServicio;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.Usuario;
import domain.localizaciones.Direccion;
import domain.services.georef.entities.Localidad;
import domain.services.georef.entities.Municipio;
import domain.services.georef.entities.Provincia;
import domain.services.notificationSender.ComponenteNotificador;
import domain.services.notificationSender.NotificarViaCorreo;
import domain.services.notificationSender.NotificarViaWpp;
import domain.servicios.Servicio;
import io.javalin.http.Context;
import sever.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PerfilController
{
    RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

    RepositorioDireccion repositorioDireccion = new RepositorioDireccion();

    RepositorioServicio repositorioServicio = new RepositorioServicio();
    public void redirectPerfil(Context context)
    {

        int profileUserId = Integer.parseInt(context.pathParam("id"));
        if(repositorioUsuario.findMiembroByUsuarioId(profileUserId)!= null)
            renderPerfilMiembro(context);
        else if (repositorioUsuario.findOrganismoDeControlByUserId(profileUserId)!= null)
            renderPerfilODC(context);
        else if(repositorioUsuario.findEntidadPrestadoraByUserId(profileUserId) != null)
            renderPerfilEP(context);
            else
            context.redirect("/");
        /*
        switch (context.formParam("PerfilType")) {
            case "Miembro":
                renderPerfilMiembro(context);
                break;
            /*case "OrganismoDeControl":
                processSignInOrganismoDeControl(context);
                break;
            case "EntidadPrestadora":
                processSignInEntidadPrestadora(context);
                break;
            default:
                context.redirect("/");
                break;
        }+/

         */
    }
    private void renderPerfilEP(Context context)
    {
        EntidadPrestadora entidadPrestadora;

    }

    private void renderPerfilODC(Context context)
    {

    }

    private void renderPerfilMiembro(Context context)
    {
        Map<String, Object> model = new HashMap<>();

        int userId = Integer.parseInt(context.cookie("id"));
        int profileUserId = Integer.parseInt(context.pathParam("id"));

        model.put("owner", userId==profileUserId);
        model.put("profileId",profileUserId);
        Usuario user = repositorioUsuario.findUsuarioById(userId);

        model.put("username", context.cookie("username"));

        Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(profileUserId);
        miembro.getServiciosQueAfectan().size();
        model.put("miembro",miembro);

        model.put("configuracionNotificacionDeIncidentes", miembro.getConfiguracionNotificacionDeIncidentes());

        model.put("provincias",repositorioDireccion.findAllProvincias());
        model.put("localidades",repositorioDireccion.findAllLocalidades());
        model.put("municipios",repositorioDireccion.findAllMunicipios());

        //model.put("configuracionNotificacionDeIncidentes", miembro.getConfiguracionNotificacionDeIncidentes());

        model.put("listaServicios",repositorioServicio.findAll());
        try {
            // Configura el loader para buscar plantillas en el directorio /templates
            Handlebars handlebars = new Handlebars().with(new ClassPathTemplateLoader("/templates", ".hbs"));

            Template templateCrearDireccion = handlebars.compile("common_CrearDireccion");
            String formBodyContent = templateCrearDireccion.apply(model);
            model.put("crearDireccion", formBodyContent);


            // Compila el contenido del partial 'detalle_comunidad' y pásalo como 'body'
            Template template = handlebars.compile("Miembro_Perfil");
            String bodyContent = template.apply(model);
            model.put("body", bodyContent);
        } catch (IOException e) {
            e.printStackTrace();
            context.status(500).result("Error al procesar la plantilla.");
            return;
        }

        context.render("layout_comun.hbs", model);

    }


    public void addLugarDeInteres(Context context)
    {
        int userId = Integer.parseInt(context.cookie("id"));
        Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(userId);
        String provinciaIdRaw = context.formParam("provinciaId");
        String localidadIdRaw = context.formParam("localidadId");
        String municipioIdRaw = context.formParam("municipioId");

        Provincia provincia = repositorioDireccion.findProvincia(Integer.parseInt(provinciaIdRaw));
        Direccion direccion;
        if(municipioIdRaw.equals(""))
        {
            direccion = new Direccion(provincia);
        }
        else
        {
            Municipio municipio = repositorioDireccion.findMunicipio(Integer.parseInt(municipioIdRaw));
            if(localidadIdRaw.equals(""))
            {
                direccion = new Direccion(provincia, municipio);
            }
            else
            {
                Localidad localidad = repositorioDireccion.findLocalidad(Integer.parseInt(localidadIdRaw));
                direccion = new Direccion(provincia,municipio,localidad);
            }
        }
        repositorioDireccion.saveDireccion(direccion);
        miembro.addLugarDeInteres(direccion);
        repositorioUsuario.updateMiembro(miembro);

        context.redirect("/perfil/"+context.pathParam("id"));
    }

    public void addServicioDeInteres(Context context)
    {
        int miembroId = Integer.parseInt(context.pathParam("id")); // Asume que el ID del miembro está en la ruta
        int servicioId = Integer.parseInt(context.formParam("servicioId"));

        Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(miembroId);
        Servicio servicio = repositorioServicio.findServicioById(servicioId);
        miembro.addServicioDeInteres(servicio);
        repositorioUsuario.updateMiembro(miembro);
        context.redirect("/perfil/"+context.pathParam("id"));
    }

    public void agregarHorario(Context context) {
        try {
            int miembroId = Integer.parseInt(context.pathParam("idMiembro"));
            String horaString = context.formParam("nuevaHora"); // "HH:MM"

            String[] partes = horaString.split(":");
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);
            int totalMinutos = horas * 60 + minutos;

            Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(miembroId);
            if (miembro != null) {
                ConfiguracionNotificacionDeIncidentes configuracion = miembro.getConfiguracionNotificacionDeIncidentes();
                configuracion.getHorarioPreferencia().add((float)totalMinutos);

                repositorioUsuario.updateMiembro(miembro);

            }

            context.redirect("/perfil/" + miembroId);
        } catch (NumberFormatException e) {
            context.status(400).result("Formato de hora no válido.");
        } catch (Exception e) {
            context.status(500).result("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    public void borrarHorario(Context context) {
        try {
            int miembroId = Integer.parseInt(context.pathParam("idMiembro"));
            float horarioABorrar = Float.parseFloat(context.formParam("horario"));

            Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(miembroId);
            if (miembro != null) {
                ConfiguracionNotificacionDeIncidentes configuracion = miembro.getConfiguracionNotificacionDeIncidentes();
                configuracion.getHorarioPreferencia().remove(horarioABorrar);

                repositorioUsuario.updateMiembro(miembro);
            }

            context.redirect("/perfil/" + miembroId);
        } catch (NumberFormatException e) {
            context.status(400).result("Formato de horario no válido.");
        } catch (Exception e) {
            context.status(500).result("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    public void guardarMedioPreferido(Context context)
    {
        String medioPreferido = context.formParam("medioPreferido");
        int miembroId = Integer.parseInt(context.pathParam("idMiembro"));
        Miembro miembro = repositorioUsuario.findMiembroByUsuarioId(miembroId);



        // Actualizar el medio preferido
        if ("mail".equals(medioPreferido)) {
            miembro.getConfiguracionNotificacionDeIncidentes().setMedioPreferido(new NotificarViaCorreo());
            // Configurar para notificaciones por correo electrónico
        } else if ("whatsapp".equals(medioPreferido))
        {
            miembro.getConfiguracionNotificacionDeIncidentes().setMedioPreferido(new NotificarViaWpp());

            // Configurar para notificaciones por WhatsApp
        }

        repositorioUsuario.updateMiembro(miembro);
        context.redirect("/perfil/"+ miembroId); // Redirigir al usuario
    }

}
