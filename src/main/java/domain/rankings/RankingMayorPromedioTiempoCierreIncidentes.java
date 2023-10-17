package domain.rankings;

import domain.Repositorios.RepositorioIncidente;
import domain.Repositorios.RepositorioLeaderBoard;
import domain.entidades.Entidad;
import domain.informes.Incidente;
import domain.rankings.Leaderboard.LeaderBoardType;
import domain.rankings.Leaderboard.Leaderboard;
import domain.rankings.Leaderboard.RankLeaderBoardUnit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class RankingMayorPromedioTiempoCierreIncidentes implements Ranking{
    @Override
    public Leaderboard generarRanking() {
        // Calcular el promedio de tiempo de cierre por entidad
        List<Incidente> incidentesCerradosUltimaSemana = new RepositorioIncidente().findClosedLastWeek(); //filtro por los incidentes cerrados la ultima semana
        Map<Integer, List<Incidente>> incidentesPorEntidad = agruparPorEntidadId(incidentesCerradosUltimaSemana); //agrupo incidentes x entidadID
        List<RankLeaderBoardUnit> ranking = calcularPromedioTiempoCierrePorEntidad(incidentesPorEntidad);
       Leaderboard leaderboard = new Leaderboard(ranking, LeaderBoardType.MAYOR_PROMEDIO_TIEMPO);
        new RepositorioLeaderBoard().save(leaderboard);
        return leaderboard;
    }


    //primero agrupar los incidentes por entidad
    public Map<Integer, List<Incidente>> agruparPorEntidadId(List<Incidente> incidentes) {
       return incidentes.stream()
             .collect(Collectors.groupingBy(incidente -> incidente.getServicioAfectado().getEstablecimiento().getEntidad().getId()
              ));
   }
   // private static Map<String, List<Incidente>> agruparIncidentesPorEntidad(List<Incidente> incidentes) {
    //    Map<String, List<Incidente>> incidentesPorEntidad = new HashMap<>();


      //  for (Incidente incidente : incidentes) {
            //TODO: here
        //    String entidad = new RepositorioIncidente().findClosedLastWeek().
                  //  incidente.getServicioAfectado().getLocalizacion().toString();//.getEntidad().getNombre();

          //  if (!incidentesPorEntidad.containsKey(entidad)) {
            //    incidentesPorEntidad.put(entidad, new ArrayList<>());}

          //  incidentesPorEntidad.get(entidad).add(incidente);}

        //return incidentesPorEntidad;}
        private List<RankLeaderBoardUnit> calcularPromedioTiempoCierrePorEntidad(Map<Integer, List<Incidente>> incidentesPorEntidad) {
    List<RankLeaderBoardUnit> ranking = new ArrayList<>();
    for (Map.Entry<Integer, List<Incidente>> entry : incidentesPorEntidad.entrySet()) {

        ranking.add(new RankLeaderBoardUnit(
                entry.getKey(),
                entry.getValue().stream()
                        .mapToDouble(this::calcularTiempoCierre).sum()/entry.getValue().size()
        ));
        /*int entidadId = entry.getKey();
        List<Incidente> incidentesEntidad = entry.getValue();

        long tiempoCierreTotal = 0;
        int cantidadIncidentes = 0;

        for (Incidente incidente : incidentesEntidad) {
            // Calcular el tiempo de cierre de cada incidente
            long tiempoCierre = calcularTiempoCierre(incidente);
            tiempoCierreTotal += tiempoCierre;
            cantidadIncidentes++;
        }

        if (cantidadIncidentes > 0) {
            long promedioTiempoCierre = tiempoCierreTotal / cantidadIncidentes;
            ranking.add(new EntidadPromedio(entidadId, promedioTiempoCierre));
        }*/
    }

    // Ordenar el ranking por promedio de tiempo de cierre descendente
    //ranking.sort(Comparator.comparing(EntidadPromedio::getPromedio).reversed());

    return ranking;
}

    // Función para calcular el tiempo de cierre
    private long calcularTiempoCierre(Incidente incidente) {
        LocalDateTime fechaApertura = incidente.getFechaInicio();
        LocalDateTime fechaCierre = incidente.getFechaCierre();
            long tiempoCierre= fechaCierre.getDayOfYear()- fechaApertura.getDayOfYear();
        return tiempoCierre;
    }

   /* private List<EntidadPromedio> calcularPromedioTiempoCierrePorEntidad(Map<Integer, List<Incidente>> incidentesPorEntidad) {
        List<EntidadPromedio> rankingSemana = new ArrayList<>();

        for (Map.Entry<String, List<Incidente>> entry : incidentesPorEntidad.entrySet()) {
            String entidad = entry.getKey();
            List<Incidente> incidentesEntidad = entry.getValue();

            long tiempoCierreTotal = 0;
            int cantidadIncidentes = 0;

            for (Incidente incidente : incidentesEntidad) {
                LocalDateTime fechaApertura = incidente.getFechaInicio();
                LocalDateTime fechaCierre = incidente.getFechaCierre();

                if (fechaCierre != null && fechaCierre.isBefore(fechaApertura.plusDays(7))) {
                    long tiempoCierre = fechaCierre.getDayOfYear() - fechaApertura.getDayOfYear();
                    tiempoCierreTotal += tiempoCierre;
                    cantidadIncidentes++;
                }
            }

            if (cantidadIncidentes > 0) {
                long promedioTiempoCierre = tiempoCierreTotal / cantidadIncidentes;
                rankingSemana.add(new EntidadPromedio(entidad, promedioTiempoCierre));
            }
        }

        // Ordenar el ranking por promedio de tiempo de cierre descendente
        rankingSemana.sort(Comparator.comparing(EntidadPromedio::getPromedio).reversed());

        return rankingSemana;
    }
*/
     public class EntidadPromedio{
        private Integer entidadID;
        private long promedio;

         public EntidadPromedio(Integer entidadID, long promedioTiempoCierre) {
             this.entidadID = entidadID;
             this.promedio = promedioTiempoCierre;
         }


         public long getPromedio() {
             return promedio;
         }

     }

}

