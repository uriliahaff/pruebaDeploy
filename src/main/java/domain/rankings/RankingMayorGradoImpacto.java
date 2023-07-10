package domain.rankings;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.repositorios.RepositorioComunidades;
import domain.services.notificadorDeIncidentes.CommandoNotificacion;
import domain.servicios.PrestacionDeServicio;
import domain.servicios.Servicio;
import domain.informes.Incidente;

import java.util.*;

public class RankingMayorGradoImpacto implements Ranking {
    @Override
    public void generarRanking(List<Incidente> incidentes) {
        // Calcular el grado de impacto por entidad
        Map<String, Integer> gradoImpactoPorEntidad = calcularGradoImpactoPorEntidad(incidentes);

        // Crear una lista de entidades con su grado de impacto
        List<EntidadGradoImpacto> rankingSemana = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : gradoImpactoPorEntidad.entrySet()) {
            String entidad = entry.getKey();
            int gradoImpacto = entry.getValue();
            rankingSemana.add(new EntidadGradoImpacto(entidad, gradoImpacto));
        }

        // Ordenar el ranking por grado de impacto descendente
        rankingSemana.sort(Comparator.comparing(EntidadGradoImpacto::getGradoImpacto).reversed());

        // Imprimir el ranking semanal
        System.out.println("Ranking semanal por mayor grado de impacto:");
        int posicion = 1;
        for (EntidadGradoImpacto entidadGrado : rankingSemana) {
            System.out.println(posicion + ". " + entidadGrado.getEntidad() + " - Grado de impacto: " + entidadGrado.getGradoImpacto());
            posicion++;
        }
    }

    private Map<String, Integer> calcularGradoImpactoPorEntidad(List<Incidente> incidentes) {
        Map<String, Integer> gradoImpactoPorEntidad = new HashMap<>();

        for (Incidente incidente : incidentes) {
            String entidad = incidente.getServicioAfectado().getLocalizacion().getEntidad().getNombre();
            int miembrosComunidad = obtenerMiembrosComunidad(incidente);

            if (gradoImpactoPorEntidad.containsKey(entidad)) {
                int gradoImpactoExistente = gradoImpactoPorEntidad.get(entidad);
                gradoImpactoPorEntidad.put(entidad, gradoImpactoExistente + miembrosComunidad);
            } else {
                gradoImpactoPorEntidad.put(entidad, miembrosComunidad);
            }
        }

        return gradoImpactoPorEntidad;
    }

    private int obtenerMiembrosComunidad(Incidente incidente) { //No se si hay q filtrar comunidades x lugar
        PrestacionDeServicio servicioAfectado = incidente.getServicioAfectado();
        String lugarAfectado = servicioAfectado.getLocalizacion().toString();
        Comunidad comunidad = RepositorioComunidades.getInstance().obtenerComunidadPorLugar(lugarAfectado);

        if (comunidad != null) {
            List<Miembro> miembrosFiltrados = comunidad.miembrosFiltradosPorInteresEnLocalizacion(lugarAfectado);
            return miembrosFiltrados.size();
        }

        return 0;
    }

    public class EntidadGradoImpacto {
        private String entidad;
        private int gradoImpacto;

        public EntidadGradoImpacto(String entidad, int gradoImpacto) {
            this.entidad = entidad;
            this.gradoImpacto = gradoImpacto;
        }

        public String getEntidad() {
            return entidad;
        }

        public int getGradoImpacto() {
            return gradoImpacto;
        }
    }
}