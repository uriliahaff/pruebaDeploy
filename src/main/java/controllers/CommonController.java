package controllers;

import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Usuario;
import domain.services.NavBarVisualizer;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonController
{
    public static Map<String, Object> fillNav(Map<String, Object> model, Usuario user)
    {
        NavBarVisualizer navBarVisualizer = new NavBarVisualizer();
        model.put("itemsNav", navBarVisualizer.itemsNav(user.getRoles()));

        return model;
    }
}
