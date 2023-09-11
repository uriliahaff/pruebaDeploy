package domain.rankings;

import domain.entidades.Entidad;
import domain.informes.Incidente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class RankingMayorPromedioTiempoCierreIncidentes implements Ranking{
    @Override
    public void generarRanking(List<Incidente> incidentes) {
        // Calcular el promedio de tiempio de cierre por entidad
        Map<String, List<Incidente>> incidentesPorEntidad = agruparIncidentesPorEntidad(incidentes);
        List<EntidadPromedio> rankingSemana = calcularPromedioTiempoCierrePorEntidad(incidentesPorEntidad);
    }

    private static Map<String, List<Incidente>> agruparIncidentesPorEntidad(List<Incidente> incidentes) {
        Map<String, List<Incidente>> incidentesPorEntidad = new HashMap<>();


        for (Incidente incidente : incidentes) {
            //TODO: here
            String entidad = incidente.getServicioAfectado().getLocalizacion().toString();//.getEntidad().getNombre();

            if (!incidentesPorEntidad.containsKey(entidad)) {
                incidentesPorEntidad.put(entidad, new ArrayList<>());
            }

            incidentesPorEntidad.get(entidad).add(incidente);
        }

        return incidentesPorEntidad;
    }

    private List<EntidadPromedio> calcularPromedioTiempoCierrePorEntidad(Map<String, List<Incidente>> incidentesPorEntidad) {
        List<EntidadPromedio> rankingSemana = new ArrayList<>();

        for (Map.Entry<String, List<Incidente>> entry : incidentesPorEntidad.entrySet()) {
            String entidad = entry.getKey();
            List<Incidente> incidentesEntidad = entry.getValue();

            long tiempoCierreTotal = 0;
            int cantidadIncidentes = 0;

            for (Incidente incidente : incidentesEntidad) {
                LocalDate fechaApertura = incidente.getFechaInicio();
                LocalDate fechaCierre = incidente.getFechaCierre();

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

     public class EntidadPromedio{
        private String entidad;
        private long promedio;

         public EntidadPromedio(String entidad, long promedioTiempoCierre) {
             this.entidad = entidad;
             this.promedio = promedioTiempoCierre;
         }


         public long getPromedio() {
             return promedio;
         }

     }

}

