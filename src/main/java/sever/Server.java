package sever;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import domain.other.EntityManagerProvider;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import sever.handlers.AppHandlers;
import sever.middlewares.AuthMidddleware;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.jknack.handlebars.io.TemplateLoader;

import javax.persistence.EntityManagerFactory;


public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if(app == null)
            throw new RuntimeException("sever.App no inicializada");
        return app;
    }

    public static void init() {
        System.out.println("Iniciando la aplicación...");
        if(app == null) {
            System.out.println("Creando la aplicación Javalin...");

            Integer port = Integer.parseInt(System.getProperty("port", "8080"));
            app = Javalin.create(config()).start(port);
            initTemplateEngine();
            AppHandlers.applyHandlers(app);
            Router.init();
        }
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });
            AuthMidddleware.aply(config);
        };
    }

/*
    private static void initTemplateEngine() {
        JavalinRenderer.register(
                (path, model, context) -> { // Función que renderiza el template
                    Handlebars handlebars = new Handlebars();
                    Template template = null;
                    try {
                        template = handlebars.compile(
                                "templates/" + path.replace(".hbs",""));
                        return template.apply(model);
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la página indicada...";
                    }
                }, ".hbs" // Extensión del archivo de template
        );
    }*/
    private static void initTemplateEngine() {
        System.out.println("Inicializando el motor de plantillas...");

        JavalinRenderer.register((filePath, model, context) -> {
            // Crear una nueva instancia de Handlebars con un TemplateLoader personalizado
            Handlebars handlebars = new Handlebars()
                    .with(new ClassPathTemplateLoader("/templates", ".hbs"));

            // Ahora Handlebars buscará las plantillas dentro de /resources/templates
            Template template;
            try {
                // Compilamos la plantilla principal pasando solo el nombre del archivo (sin la extensión .hbs)
                template = handlebars.compile(filePath.replace(".hbs", ""));
                // Aplicamos el modelo a la plantilla compilada para renderizar el HTML
                return template.apply(model);
            } catch (IOException e) {
                e.printStackTrace();
                context.status(HttpStatus.INTERNAL_SERVER_ERROR);
                return "Error al renderizar la plantilla.";
            }
        }, ".hbs");
    }



}