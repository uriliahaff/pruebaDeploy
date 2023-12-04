package sever;

import controllers.*;
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
            Rol adminEntidad = new Rol("adminEntidad", null);
            Rol adminOrganismo= new Rol("adminOrganismo", null);

            Server.app().get("/", ((LoginController) FactoryController.controller("login"))::admin);
            Server.app().get("/login", ((LoginController) FactoryController.controller("login"))::index);
            Server.app().get("/signin", ((LoginController) FactoryController.controller("login"))::registro);



            Server.app().get("/logout", ((LoginController) FactoryController.controller("login"))::logout);
            Server.app().post("/login", ((LoginController) FactoryController.controller("login"))::loginAttempt);
            Server.app().post("/signin", ((LoginController) FactoryController.controller("login"))::signinAttempt);

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
                    ((EntidadesOrganismosController) FactoryController.controller("organismos"))::cargarMasivaOrganismos
                    ,admin);
            Server.app().get("/usuarios",
                    ((UsuariosController) FactoryController.controller("usuarios"))::index);
                    //,admin);
            Server.app().get("/usuarios/{id}/editar",
                    ((UsuariosController) FactoryController.controller("usuarios"))::editar,
                    admin);
            Server.app().post("/editarUsuario/{id}",
                    ((UsuariosController) FactoryController.controller("usuarios"))::update,
                    admin);
            Server.app().post("/eliminarUsuario/{id}",
                    ((UsuariosController) FactoryController.controller("usuarios"))::delete,
                    admin);
            Server.app().get("/rankings",
                    ((RankingController) FactoryController.controller("rankings"))::index,
                    adminEntidad, adminOrganismo);
            Server.app().get("/ranking/{id}",
                    ((RankingController) FactoryController.controller("rankings"))::ranking,
                    adminEntidad, adminOrganismo);
            Server.app().get("/admin/incidentes",
                    ((IncidenteController) FactoryController.controller("incidentes"))::index,
                    admin);
            Server.app().get("/aperturaIncidentes", ((IncidenteController) FactoryController.controller("incidentes"))::aperturaIncidentes);
            Server.app().post("/aperturaIncidente", ((IncidenteController) FactoryController.controller("incidentes"))::abrirIncidente);
            Server.app().get("/incidentes", ((IncidenteController) FactoryController.controller("incidentes"))::indexIncidentes);
            //Server.app().get("/cerrarIncidente/{id}", ((IncidenteController) FactoryController.controller("incidentes"))::cerrarIncidente);
            Server.app().post("/cerrarIncidente/{id}", ((IncidenteController) FactoryController.controller("incidentes"))::cerrarIncidente);
            Server.app().get("/revisionIncidente/{id}", ((IncidenteController) FactoryController.controller("incidentes"))::revisionIncidente);


            Server.app().get("/comunidades", ((ComunidadController) FactoryController.controller("comunidad"))::indexComunidades);
            Server.app().post("/comunidad/delete/{id}", ((ComunidadController) FactoryController.controller("comunidad"))::eliminarComunidad);

            Server.app().get("/comunidad/{id}", ((ComunidadController) FactoryController.controller("comunidad"))::mostrarComunidad);

            Server.app().post("/comunidad/{comunidadId}/expulsar/{miembroId}", ((ComunidadController) FactoryController.controller("comunidad"))::expulsarMiembro);


            Server.app().post("/comunidad/{comunidadId}/ascender/{miembroId}", ((ComunidadController) FactoryController.controller("comunidad"))::ascenderAAdmin);
            Server.app().post("/comunidad/removerAdmin", ((ComunidadController) FactoryController.controller("comunidad"))::removerAdmin);

            Server.app().post("/comunidad/join/{comunidadId}", ((ComunidadController) FactoryController.controller("comunidad"))::joinComunidad);

            Server.app().post("/comunidad/addInteres", ((ComunidadController) FactoryController.controller("comunidad"))::addInteres);
            Server.app().post("/comunidad/removerInteres", ((ComunidadController) FactoryController.controller("comunidad"))::removerInteres);



            Server.app().get("/servicios", ((ServicioController) FactoryController.controller("servicios"))::indexServicios);
            Server.app().post("/crearServicio", ((ServicioController) FactoryController.controller("servicios"))::crearServicio);

            Server.app().get("/organismoDeControl", ((EntidadesOrganismosController) FactoryController.controller("organismos"))::indexOrganismos);
            Server.app().post("/cargarMasivaDeOrganismosDeControl", ((EntidadesOrganismosController) FactoryController.controller("organismos"))::cargarMasivaOrganismos);

            Server.app().get("/registrar", ((SignInController) FactoryController.controller("signIn"))::renderSignInMember);
            Server.app().get("/registrarOrganismo", ((SignInController) FactoryController.controller("signIn"))::renderSignInOrganismoDeControl);
            Server.app().get("/registrarEntidad", ((SignInController) FactoryController.controller("signIn"))::renderSignInEntidadPrestadora);
            Server.app().post("/registrarUsuario", ((SignInController) FactoryController.controller("signIn"))::processSignInRedirect);

            Server.app().get("/perfil/{id}", ((PerfilController) FactoryController.controller("perfil"))::redirectPerfil);
            //Server.app().get("/perfil", ((PerfilController) FactoryController.controller("perfil"))::redirectPerfil); // MAS SEGURO

            Server.app().post("/perfil/{id}/addLugarInteres", ((PerfilController) FactoryController.controller("perfil"))::addLugarDeInteres);
            Server.app().post("/perfil/{id}/addService", ((PerfilController) FactoryController.controller("perfil"))::addServicioDeInteres);
            Server.app().post("/perfil/{idMiembro}/agregarHorario", ((PerfilController) FactoryController.controller("perfil"))::agregarHorario);
            Server.app().post("/perfil/{idMiembro}/borrarHorario", ((PerfilController) FactoryController.controller("perfil"))::borrarHorario);
            Server.app().post("/perfil/{idMiembro}/updateNotificationPreferences", ((PerfilController) FactoryController.controller("perfil"))::guardarMedioPreferido);

            Server.app().get("/entidades", ((EntidadController) FactoryController.controller("entidad"))::indexEntidades);
            Server.app().get("/entidad/{id}", ((EntidadController) FactoryController.controller("entidad"))::indexEntidad);
            Server.app().post("/entidad/{id}/crearEstablecimiento", ((EntidadController) FactoryController.controller("entidad"))::crearEstablecimiento);
            Server.app().post("/entidades/crearEntidad", ((EntidadController) FactoryController.controller("entidad"))::crearEntidad);

            Server.app().get("/establecimiento/{id}", ((EstablecimientoController) FactoryController.controller("establecimiento"))::indexEstablecimiento);
            Server.app().post("/establecimiento/{id}/agregarServicio", ((EstablecimientoController) FactoryController.controller("establecimiento"))::addPrestacion);

            Server.app().get("/roles", ((RolController) FactoryController.controller("rol"))::indexRols);
            Server.app().post("/rol/crearRol", ((RolController) FactoryController.controller("rol"))::crearRol);
            Server.app().post("/rol/{id}/agregarPermiso", ((RolController) FactoryController.controller("rol"))::addPermiso);
            Server.app().post("/rol/{id}/borrarPermiso/{permisoId}", ((RolController) FactoryController.controller("rol"))::borrarPermiso);

            Server.app().get("/georef", ((GEOREFController) FactoryController.controller("georef"))::index);
            Server.app().post("/georef", ((GEOREFController) FactoryController.controller("georef"))::actualizar);


        });

        //  Server.app().get("/entidadesPrestadoras", new EntidadesPrestadorasController()::index);



    }

    private static boolean isLoggedIn(Context ctx) {
        // Verificar la presencia de la cookie de autenticación u otros indicadores de inicio de sesión
        return ctx.cookie("id") != null;
    }

}
