package domain.informes.filtros;



import domain.informes.Incidente;

import java.time.LocalDate;

public class FiltroFechaCierreEstrategia implements FiltroIncidenteEstrategia{
    private LocalDate fechaInicio;
    private LocalDate fechaCierre;

    public FiltroFechaCierreEstrategia(LocalDate fechaInicio, LocalDate fechaCierre) {
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
    }
    @Override
    public boolean cumpleCriterio(Incidente incidente) {
        return false;
    }
}
