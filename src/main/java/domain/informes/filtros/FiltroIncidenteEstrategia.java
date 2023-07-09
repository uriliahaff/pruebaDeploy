package domain.informes.filtros;

import domain.informes.Incidente;

public interface FiltroIncidenteEstrategia {
    public boolean cumpleCriterio(Incidente incidente);
}
