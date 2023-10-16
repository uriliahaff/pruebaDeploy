package domain.services.service2;

import com.google.gson.Gson;
import domain.services.service2.model.ComunidadModel;
import domain.services.service1.model.PeticionModel;
import domain.services.service1.model.PropuestaDeFusionModel;
import domain.services.service2.model.CambioDePuntajeModel;
import domain.services.service2.model.IncidenteModel;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalculadorConfianzaAPIService
{

    private Gson gson;
    private static CalculadorConfianzaAPIService instacia = new CalculadorConfianzaAPIService();
    private static final String urlAPI = "http://localhost:8082/";
    private Retrofit retrofit;
    private CalculadorConfianzaAPIService(){

        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static CalculadorConfianzaAPIService getInstancia(){
        return instacia;
    }


    public List<CambioDePuntajeModel> calcularGradosdeConfianza(List<IncidenteModel> incidenteModels) throws IOException
    {
        CalculadorConfianzaAPI calculadorConfianzaAPI = this.retrofit.create(CalculadorConfianzaAPI.class);


        Call<List<CambioDePuntajeModel>> call = calculadorConfianzaAPI.calcularGradosdeConfianza(incidenteModels);
        Response<List<CambioDePuntajeModel>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Error en la respuesta: " + response.code() + " " + response.message());
        }
    }
    public List<ComunidadModel> actualizarGradoConfianzaDeComunidades(List<ComunidadModel> comunidadModels) throws IOException
    {
        CalculadorConfianzaAPI calculadorConfianzaAPI = this.retrofit.create(CalculadorConfianzaAPI.class);

        Call<List<ComunidadModel>> call = calculadorConfianzaAPI.actualizarGradoConfianzaDeComunidades(comunidadModels);
        Response<List<ComunidadModel>> response = call.execute();
        if(response.isSuccessful())
        {
            return response.body();
        }
        else
        {
            throw new IOException("Error en la respuesta: " + response.code() + " " + response.message());

        }
    }

}
