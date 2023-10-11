package controllers;

import domain.Repositorios.RepositorioUsuario;

public class FactoryController {

    public static Object controller(String nombre){
        Object controller = null;

        switch (nombre){
            case "login": controller = new LoginController(new RepositorioUsuario()); break;
        }
        return controller;
    }


}
