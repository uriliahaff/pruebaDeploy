package domain.informes.filtros;

import domain.informes.Incidente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FiltroFechaInicioEstrategia implements FiltroIncidenteEstrategia {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;

    public FiltroFechaInicioEstrategia(LocalDateTime fechaInicio, LocalDateTime fechaCierre) {
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
    }

    @Override
    public boolean cumpleCriterio(Incidente incidente) {
        LocalDateTime fechaIncidente = incidente.getFechaCierre();
        return fechaIncidente != null && !(fechaIncidente.isBefore(fechaInicio) || fechaIncidente.isAfter(fechaCierre));
    }
}