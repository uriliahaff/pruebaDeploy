package controllers;

import domain.Usuarios.Usuario;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingController {

    public void index(Context context){
        Map<String, Object> model = new HashMap<>();

        model.put("username", context.cookie("username"));

        context.render("rankings.hbs", model);
    }

    public void ranking(Context context){
        Map<String, Object> model = new HashMap<>();

        model.put("username", context.cookie("username"));

        context.render("ranking.hbs", model);
    }



}
