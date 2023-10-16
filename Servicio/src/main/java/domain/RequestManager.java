package domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;

public class RequestManager
{
    public static void main(String[] args)
    {
        Integer port = Integer.parseInt(System.getProperty("port", "8082"));
        Javalin app = Javalin.create().start(port);
        app.get("/",ctx -> ctx.result("Hola Mundo") );
        /*
        app.post("/calcularGradosDeConfianza", ctx -> {
            List<Incidente> incidentes = ctx.bodyAsClass(List.class); // Suponiendo que el cuerpo del mensaje es una lista de Incidentes en formato JSON
            List<CambioDePuntaje> cambios = CalculadorDeCambiosGradosDeConfianza.calcularGradosdeConfianza(incidentes);
            ctx.json(cambios);
        });*/
        app.post("/calcularGradosDeConfianza", ctx -> {
            ObjectMapper mapper = new ObjectMapper();
            List<Incidente> incidentes = mapper.readValue(ctx.body(), new TypeReference<List<Incidente>>(){});
            List<CambioDePuntaje> cambios = CalculadorDeCambiosGradosDeConfianza.calcularGradosdeConfianza(incidentes);
            ctx.json(cambios);
        });
        app.post("/actualizarGradoConfianzaDeComunidades", ctx -> {
            ObjectMapper mapper = new ObjectMapper();
            List<Comunidad> comunidades = mapper.readValue(ctx.body(), new TypeReference<List<Comunidad>>(){});
            CalculadorDeCambiosGradosDeConfianza.actualizarGradoConfianzaDeComunidades(comunidades);
            ctx.json(comunidades);
        });

    }

}
