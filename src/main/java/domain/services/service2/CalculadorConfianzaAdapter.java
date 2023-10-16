package domain.services.service2;

import domain.informes.Incidente;
import domain.services.service2.model.CambioDePuntajeModel;
import domain.services.service2.model.IncidenteModel;

import java.io.IOException;
import java.util.List;

public class CalculadorConfianzaAdapter
{
    CalculadorConfianzaAPIService calculadorConfianzaAPIService = CalculadorConfianzaAPIService.getInstancia();
    public List<CambioDePuntaje> obternerCambios(List<Incidente> incidentes) throws IOException {
        List<IncidenteModel> incidenteModels = incidentes.stream().map(IncidenteModel::new).toList();
        List<CambioDePuntajeModel> cambioDePuntajeModels = calculadorConfianzaAPIService.calcularGradosdeConfianza(incidenteModels);
        return cambioDePuntajeModels.stream().map(CambioDePuntaje::new).toList();
    }
}
