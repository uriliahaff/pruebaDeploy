package controllers;

import domain.Repositorios.RepositorioEntidadPrestadoraOrganismoControl;
import domain.Repositorios.RepositorioIncidente;
import domain.Repositorios.RepositorioUsuario;

public class FactoryController {

    public static Object controller(String nombre){
        Object controller = null;



        switch (nombre){
            case "login": controller = new LoginController(new RepositorioUsuario()); break;
            case "entidades": controller = new EntidadesOrganismosController(new RepositorioEntidadPrestadoraOrganismoControl()); break;
            case "organismos": controller = new EntidadesOrganismosController(new RepositorioEntidadPrestadoraOrganismoControl()); break;
            case "usuarios": controller = new UsuariosController(new RepositorioUsuario()); break;
            case "rankings": controller = new RankingController(); break;
            case "incidentes": controller = new IncidenteController(new RepositorioIncidente()); break;

        }
        return controller;
    }


}
