package domain.rankings;

import domain.informes.Incidente;

import java.util.List;

public interface Ranking {

    public void generarRanking(List<Incidente> incidentes);
}
