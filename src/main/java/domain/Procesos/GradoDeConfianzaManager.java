package domain.Procesos;

import domain.informes.Incidente;
import domain.services.service2.CalculadorConfianzaAPIService;
import domain.services.service2.CalculadorConfianzaAdapter;
import domain.services.service2.CambioDePuntaje;

import java.io.IOException;
import java.util.List;

public class GradoDeConfianzaManager
{
    public CalculadorConfianzaAdapter calculadorConfianzaAdapter = CalculadorConfianzaAdapter.getInstance();

    public void updateGradoDeConfianza(List<Incidente> incidentes) throws IOException {
        List<CambioDePuntaje> cambioDePuntajes = calculadorConfianzaAdapter.obternerCambios(incidentes);
        cambioDePuntajes.forEach(CambioDePuntaje::apply);
    }

}
