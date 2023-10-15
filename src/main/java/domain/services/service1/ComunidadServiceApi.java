package domain.services.service1;

import domain.Usuarios.Usuario;
import domain.services.service1.model.ComunidadModel;
import domain.services.service1.model.PeticionModel;
import domain.services.service1.model.PropuestaDeFusionModel;
import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.Path;

import java.util.ArrayList;

public interface ComunidadServiceApi
{
    @GET("comunidad/prueba")
    Call<String> getPrueba();
    //@GET("comunidad/sugerirFusiones")
    //Call<ArrayList<PropuestaDeFusionModel>> sugerirFusiones(@Body String serializedPeticion);
    //@HTTP(method = "POST", path = "comunidad/sugerirFusiones", hasBody = true)
    @POST("comunidad/sugerirFusiones")
    Call<ArrayList<PropuestaDeFusionModel>> sugerirFusiones(@Body PeticionModel peticionModel);


    @POST("comunidad/fusionarComunidades")
    Call<ComunidadModel> fusionarComunidades(@Body PropuestaDeFusionModel propuestaDeFusionModel);

}
