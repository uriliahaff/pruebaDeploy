package domain.services.service1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.services.georef.GeorefService;
import domain.services.georef.ServicioGeoref;
import domain.services.georef.entities.ListadoDeMunicipios;
import domain.services.georef.entities.ListadoDeProvincias;
import domain.services.service1.Serializer.LocalDateTimeAdapter;
import domain.services.service1.model.ComunidadModel;
import domain.services.service1.model.PeticionModel;
import domain.services.service1.model.PropuestaDeFusionModel;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComunidadApiService
{
    private Gson gson;
    private static ComunidadApiService instacia = new ComunidadApiService();
    private static final String urlAPI = "http://localhost:8085/";
    private Retrofit retrofit;
    private ComunidadApiService(){
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static ComunidadApiService getInstancia(){
        return instacia;
    }

    public ArrayList<PropuestaDeFusionModel> obtenerPropuestas(PeticionModel peticionModel) throws IOException {
        ComunidadServiceApi comunidadServiceApi = this.retrofit.create(ComunidadServiceApi.class);

        //String serializedPeticion = gson.toJson(peticionModel);

        //System.out.println(serializedPeticion);

        Call<ArrayList<PropuestaDeFusionModel>> call = comunidadServiceApi.sugerirFusiones(peticionModel);
        Response<ArrayList<PropuestaDeFusionModel>> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Error en la respuesta: " + response.code() + " " + response.message());
        }
    }

    public ComunidadModel fusionarComunidades(PropuestaDeFusionModel propuesta) throws IOException {
        ComunidadServiceApi comunidadServiceApi = this.retrofit.create(ComunidadServiceApi.class);
        Call<ComunidadModel> call = comunidadServiceApi.fusionarComunidades(propuesta);
        Response<ComunidadModel> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Error en la respuesta: " + response.code() + " " + response.message());
        }
    }

    public String obtenerPrueba() throws IOException {

        ComunidadServiceApi comunidadServiceApi = this.retrofit.create(ComunidadServiceApi.class);
        Call<String> call = comunidadServiceApi.getPrueba();
        System.out.println("Wait here");
        try {
            Response<String> response = call.execute();


        System.out.println("Search here");

            if (response.isSuccessful()) {
                System.out.println("and search here");

                System.out.println(response.raw().body().string());
                return response.body();
            } else {
                throw new IOException("Error en la respuesta: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
