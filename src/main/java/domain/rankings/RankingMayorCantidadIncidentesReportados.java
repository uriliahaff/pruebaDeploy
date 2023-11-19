package domain.rankings;

import domain.Repositorios.RepositorioEntidad;
import domain.Repositorios.RepositorioIncidente;
import domain.Repositorios.RepositorioLeaderBoard;
import domain.entidades.Entidad;
import domain.informes.Incidente;
import domain.rankings.Leaderboard.LeaderBoardType;
import domain.rankings.Leaderboard.Leaderboard;
import domain.rankings.Leaderboard.RankLeaderBoardUnit;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class RankingMayorCantidadIncidentesReportados implements Ranking
{
    @Override
    public Leaderboard generarRanking() {
        /*Map<String, Integer> incidentesPorEntidad = new HashMap<>();
        Set<String> incidentesDuplicados = new HashSet<>();*/
        List<Incidente> incidetesAbiertosUltimaSemana = new RepositorioIncidente().findOpenedLastWeek();
        List<Incidente> incidentesFiltrados = eliminarDuplicados(incidetesAbiertosUltimaSemana);
        Map<Integer, List<Incidente>> incidentesPorEntidad = agruparPorEntidadId(
                incidentesFiltrados
        );
        Leaderboard leaderboard = generarRangkings(incidentesPorEntidad);

        new RepositorioLeaderBoard().save(leaderboard);
        new RepositorioLeaderBoard().update(leaderboard);
    return leaderboard;
    }
    public Map<Integer, List<Incidente>> agruparPorEntidadId(List<Incidente> incidentes) {
        return incidentes.stream()
                .collect(Collectors.groupingBy(
                        incidente -> incidente.getServicioAfectado().getEstablecimiento().getEntidad().getId()
                ));
    }

    public Leaderboard generarRangkings(Map<Integer,List<Incidente>> ranks)
    {
        List<RankLeaderBoardUnit> rankDetails = new ArrayList<>();

        for (Map.Entry<Integer, List<Incidente>> entry : ranks.entrySet()) {
            //RankLeaderBoard rankDetail = new R();
            rankDetails.add(new RankLeaderBoardUnit(
                    entry.getKey(),
                    entry.getValue().size()
            ));

        }
        return new Leaderboard(rankDetails, LeaderBoardType.MAYORCANTIDADINCIDENTESREPORTADOS);
    }
    public Map<Entidad, Integer> ordenarEntidadesPorIncidentesAbiertos(Map<Integer, List<Incidente>> mapaEntidadIncidentes) {
        RepositorioEntidad repositorioEntidad = new RepositorioEntidad();

        return mapaEntidadIncidentes.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()))
                .collect(Collectors.toMap(
                        entry -> repositorioEntidad.findEntidadById(entry.getKey()),  // Convertir el ID a la Entidad
                        entry -> entry.getValue().size(),  // Tamaño de la lista de incidentes
                        (e1, e2) -> e1,  // En caso de duplicados (no debería ocurrir, pero es necesario para el método toMap)
                        LinkedHashMap::new  // Mantener el orden de inserción (ordenado)
                ));
    }

    public List<Incidente> eliminarDuplicados(List<Incidente> incidentes) {
        // Ordenamos por fecha de reporte en orden ascendente
        incidentes.sort(Comparator.comparing(Incidente::getFechaInicio));

        List<Incidente> incidentesUnicos = new ArrayList<>();

        for (Incidente incidenteActual : incidentes) {
            boolean esDuplicado = incidentesUnicos.stream().anyMatch(incidenteRegistrado ->
                    incidenteRegistrado.getServicioAfectado().getId() == incidenteActual.getServicioAfectado().getId() &&
                            Duration.between(incidenteRegistrado.getFechaInicio(), incidenteActual.getFechaInicio()).toHours() <= 24 &&
                            (incidenteRegistrado.estaAbierto() ||
                                    incidenteRegistrado.getFechaCierre().isBefore(incidenteActual.getFechaInicio()))
            );

            if (!esDuplicado) {
                incidentesUnicos.add(incidenteActual);
            }
        }

        return incidentesUnicos;
    }

}
