package domain.informes.filtros;

import domain.informes.Incidente;

import java.time.LocalDate;

public class FiltroFechaInicioEstrategia implements FiltroIncidenteEstrategia {

    private LocalDate fechaInicio;
    private LocalDate fechaCierre;

    public FiltroFechaInicioEstrategia(LocalDate fechaInicio, LocalDate fechaCierre) {
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
    }

    @Override
    public boolean cumpleCriterio(Incidente incidente) {
        LocalDate fechaIncidente = incidente.getFechaCierre();
        return fechaIncidente != null && !(fechaIncidente.isBefore(fechaInicio) || fechaIncidente.isAfter(fechaCierre));
    }
}