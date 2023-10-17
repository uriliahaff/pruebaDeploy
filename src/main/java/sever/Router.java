package sever;

import controllers.*;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Rol;
import io.javalin.http.Context;

public class Router {

    public static void init() {

      /*  Server.app().before(ctx -> {
            // Excluir la verificación de autenticación para la ruta /login
            if (!ctx.path().equals("/login") && !isLoggedIn(ctx)) {
                // Redirigir o manejar la falta de autenticación según tus necesidades
                ctx.redirect("/login");
            }
            if (ctx.path().equals("/login") && isLoggedIn(ctx)) {
                // Redirigir o manejar la falta de autenticación según tus necesidades
                ctx.redirect("/");
            }
        });
*/
        Server.app().routes(()-> {

            Rol admin = new Rol("admin", null);

            Server.app().get("/", ((LoginController) FactoryController.controller("login"))::admin);
            Server.app().get("/login", ((LoginController) FactoryController.controller("login"))::index);
            Server.app().get("/logout", ((LoginController) FactoryController.controller("login"))::logout);
            Server.app().post("/login", ((LoginController) FactoryController.controller("login"))::loginAttempt);
            Server.app().get("/cargaEntidades",
                    ((EntidadesOrganismosController) FactoryController.controller("entidades"))::indexEntidades,
                    admin);
            Server.app().get("/cargaOrganismos",
                    ((EntidadesOrganismosController) FactoryController.controller("organismos"))::indexOrganismos,
                    admin);
            Server.app().post("/cargarCSVEntidades",
                    ((EntidadesOrganismosController) FactoryController.controller("entidades"))::cargarMasivaEntidades,
                    admin);
            Server.app().post("/cargarCSVOrganismos",
                    ((EntidadesOrganismosController) FactoryController.controller("organismos"))::cargarMasivaOrganismos,
                    admin);
            Server.app().get("/usuarios",
                    ((UsuariosController) FactoryController.controller("usuarios"))::index,
                    admin);
            Server.app().get("/usuarios/{id}/editar",
                    ((UsuariosController) FactoryController.controller("usuarios"))::editar,
                    admin);
            Server.app().post("/editarUsuario/{id}",
                    ((UsuariosController) FactoryController.controller("usuarios"))::update,
                    admin);
            Server.app().post("/eliminarUsuario/{id}",
                    ((UsuariosController) FactoryController.controller("usuarios"))::delete,
                    admin);
            Server.app().get("/rankings", ((RankingController) FactoryController.controller("rankings"))::index);
            Server.app().get("/ranking/{id}", ((RankingController) FactoryController.controller("rankings"))::ranking);
            Server.app().get("/admin/incidentes",
                    ((IncidenteController) FactoryController.controller("incidentes"))::index,
                    admin);
            Server.app().get("/aperturaIncidentes", ((IncidenteController) FactoryController.controller("incidentes"))::aperturaIncidentes);
            Server.app().post("/aperturaIncidente", ((IncidenteController) FactoryController.controller("incidentes"))::abrirIncidente);
            Server.app().get("/incidentes", ((IncidenteController) FactoryController.controller("incidentes"))::indexUser);
            Server.app().get("/cerrarIncidente/{id}", ((IncidenteController) FactoryController.controller("incidentes"))::cerrarIncidente);
        });

        //  Server.app().get("/entidadesPrestadoras", new EntidadesPrestadorasController()::index);



    }

    private static boolean isLoggedIn(Context ctx) {
        // Verificar la presencia de la cookie de autenticación u otros indicadores de inicio de sesión
        return ctx.cookie("id") != null;
    }

}
