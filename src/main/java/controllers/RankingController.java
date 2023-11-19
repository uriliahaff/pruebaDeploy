package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioLeaderBoard;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Usuario;
import domain.rankings.Leaderboard.LeaderBoardType;
import domain.rankings.Leaderboard.Leaderboard;
import domain.rankings.Leaderboard.RankLeaderBoardUnit;
import domain.services.NavBarVisualizer;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingController {
    private RepositorioUsuario repositorioUsuario;
    private RepositorioLeaderBoard repositorioLeaderBoard = new RepositorioLeaderBoard();


    public RankingController(RepositorioUsuario repoUsuario){
        this.repositorioUsuario = repoUsuario;
    }

    public void index(Context context){
        Map<String, Object> model = new HashMap<>();

        model.put("username", context.cookie("username"));

        List<RankLeaderBoardUnit> rankLeaderBoardUnitList = getLastRanking(LeaderBoardType.MAYOR_PROMEDIO_TIEMPO);
        rankLeaderBoardUnitList.forEach(r -> r.getValue());
        model.put("rankingPromedioTiempo", rankLeaderBoardUnitList);

        rankLeaderBoardUnitList = getLastRanking(LeaderBoardType.MAYOR_GRADO_IMPACTO);
        rankLeaderBoardUnitList.forEach(r -> r.getValue());
        model.put("rankingMayorImpacto", rankLeaderBoardUnitList);

        rankLeaderBoardUnitList = getLastRanking(LeaderBoardType.MAYORCANTIDADINCIDENTESREPORTADOS);
        rankLeaderBoardUnitList.forEach(r -> r.getValue());
        model.put("rankingMayorIncidentesReportados", rankLeaderBoardUnitList);



        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("rankings.hbs", model);
    }
    private List<RankLeaderBoardUnit> getLastRanking(LeaderBoardType type)
    {
        Leaderboard leaderboard = repositorioLeaderBoard
                .findLatestLeaderboardByType(type);
        if(leaderboard != null)
            return leaderboard.getRankLeaderBoardUnits();
        return new ArrayList<RankLeaderBoardUnit>();
    }
    public void ranking(Context context){
        Map<String, Object> model = new HashMap<>();

        model.put("username", context.cookie("username"));
        Usuario user = repositorioUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));
        context.render("ranking.hbs", model);
    }



}
