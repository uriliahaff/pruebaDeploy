package domain.rankings;

import domain.informes.Incidente;

import java.util.*;

public class RankingMayorCantidadIncidentesReportados implements Ranking{
    @Override
    public void generarRanking(List<Incidente> incidentes) {
        Map<String, Integer> incidentesPorEntidad = new HashMap<>();
        Set<String> incidentesDuplicados = new HashSet<>();

    }
}
