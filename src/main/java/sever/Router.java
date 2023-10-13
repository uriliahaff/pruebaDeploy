package sever;

import controllers.EntidadesOrganismosController;
import controllers.FactoryController;
import controllers.LoginController;
import domain.Repositorios.RepositorioUsuario;

public class Router {

    public static void init() {
        Server.app().get("/", ctx -> {
            String res = "Hola ";
            if(ctx.sessionAttribute("nombre")!=""){
                res+=ctx.sessionAttribute("nombre");
            }else{
                res+="mundo";
            }
        ctx.result(res);
        });


        Server.app().get("/saludar/{nombre}", ctx -> {
            ctx.result("Hola "+ctx.pathParam("nombre")+" "+ctx.queryParam("apellido"));
        });

        Server.app().get("/nombre", ctx -> {

        ctx.sessionAttribute("nombre",ctx.queryParam("nombre"));
        ctx.result("Hola "+ctx.sessionAttribute("nombre"));
        });

        Server.app().routes(()->{
            Server.app().get("/login",((LoginController) FactoryController.controller("login"))::index);
            Server.app().post("/login",((LoginController) FactoryController.controller("login"))::loginAttempt);
            Server.app().get("/cargaEntidades",((EntidadesOrganismosController) FactoryController.controller("entidades"))::indexEntidades);
            Server.app().get("/cargaOrganismos",((EntidadesOrganismosController) FactoryController.controller("organismos"))::indexOrganismos);
            Server.app().post("/cargarCSVEntidades",((EntidadesOrganismosController) FactoryController.controller("entidades"))::cargarMasivaEntidades);


        });

        //  Server.app().get("/entidadesPrestadoras", new EntidadesPrestadorasController()::index);



    }
}
