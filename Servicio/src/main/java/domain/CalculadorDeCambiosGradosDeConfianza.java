package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculadorDeCambiosGradosDeConfianza {

    public static List<CambioDePuntaje> calcularGradosdeConfianza(List<Incidente> incidentes) {

        // Lista para almacenar los cambios de puntaje
        List<CambioDePuntaje> cambios = new ArrayList<>();


        // Iterar a través de los incidentes
        for (Incidente incidente : incidentes) {
            boolean esFraudulento = false;

            if (incidente.getUsuarioCierre() != null) {

                // Calcular la diferencia de tiempo entre apertura y cierre
                long minutosDiferencia = minutosDeDiferencia(LocalDateTime.parse(incidente.getApertura()), LocalDateTime.parse(incidente.getCierre()));

                // Verificar si es una apertura fraudulenta
                if (minutosDiferencia < 3) {
                    double descuento = 0.2;
                    CambioDePuntaje cambio = new CambioDePuntaje(incidente.getUsuarioApertura(), -descuento);
                    cambios.add(cambio);
                    esFraudulento = true;
                }

                //Verificar si es un cierre fraudulento
                // Buscar otros incidentes similares realizados por cualquier usuario
                for (Incidente otroIncidente : incidentes) {
                    if (esIncidenteSimilar(incidente, otroIncidente) && !incidente.getUsuarioCierre().equals(otroIncidente.getUsuarioApertura())) {
                        // Calcular la diferencia de tiempo entre el cierre y la apertura del incidente similar
                        long minutosDiferenciaCierreApertura = minutosDeDiferencia(LocalDateTime.parse(incidente.getCierre()), LocalDateTime.parse(otroIncidente.getApertura()));

                        // Verificar si se cumple la condición de cierre fraudulento
                        if (minutosDiferenciaCierreApertura < 3) {
                            // Aplicar descuento por cierre fraudulento
                            double descuento = 0.2;
                            CambioDePuntaje cambio = new CambioDePuntaje(incidente.getUsuarioCierre(), -descuento);
                            cambios.add(cambio);
                            esFraudulento = true;
                        }
                    }
                }

            }
            //Analizar aperturas y cierres
            if(!esFraudulento){
                CambioDePuntaje cambio = new CambioDePuntaje(incidente.getUsuarioApertura(), 0.5);
                cambios.add(cambio);
                if (incidente.getUsuarioCierre() != null) {
                    cambio = new CambioDePuntaje(incidente.getUsuarioCierre(), 0.5);
                    cambios.add(cambio);
                }
            }

        }

        // Devolver la lista de cambios de puntaje
        return cambios;
    }

    public static long minutosDeDiferencia(LocalDateTime apertura, LocalDateTime cierre){
        return java.time.Duration.between(apertura, cierre).toMinutes();
    }

    public static boolean esIncidenteSimilar(Incidente incidente, Incidente otroIncidente){
       return incidente.getPrestacionDeServicio() == otroIncidente.getPrestacionDeServicio();

    }


}
