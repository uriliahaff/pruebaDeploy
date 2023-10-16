package domain.services.service2;

import domain.informes.Incidente;
import domain.services.service2.model.ComunidadModel;
import domain.services.service2.model.CambioDePuntajeModel;
import domain.services.service2.model.IncidenteModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface CalculadorConfianzaAPI
{

    @POST("/calcularGradosDeConfianza")
    Call<List<CambioDePuntajeModel>> calcularGradosdeConfianza(@Body List<IncidenteModel> incidentes) throws IOException;

    @POST("/actualizarGradoConfianzaDeComunidades")
    Call<List<ComunidadModel>> actualizarGradoConfianzaDeComunidades(@Body List<ComunidadModel> comunidadModels) throws IOException;
}
