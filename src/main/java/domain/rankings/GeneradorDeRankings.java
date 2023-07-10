package domain.rankings;

import domain.informes.Incidente;

import java.util.List;

public class GeneradorDeRankings {
    List<Ranking> rankings;
    List<Incidente> incidentesSemanales;

    public void addRanking(Ranking ranking){
        rankings.add(ranking);
    }

    public void removeRanking(Ranking ranking){
        rankings.remove(ranking);
    }

    public void generarRankings(){
        // TODO
    }

}
