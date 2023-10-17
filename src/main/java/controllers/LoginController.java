package controllers;

import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import io.javalin.http.Context;
import io.javalin.security.RouteRole;

import java.util.*;

public class LoginController {

    private RepositorioUsuario repositorioDeUsuario;

    public LoginController(RepositorioUsuario repo){
        this.repositorioDeUsuario = repo;
    }
    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        //List<Usuario> usuarios = this.repositorioDeUsuario.buscarTodos();
        //model.put("usuarios", usuarios);

        context.render("login.hbs");
    }

    public void admin(Context context){
        Map<String, Object> model = new HashMap<>();
        System.out.println("Username: " + context.attribute("username"));

        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        Usuario user = repoUsuario.findUsuarioById(Integer.parseInt(context.cookie("id")));

        model.put("itemsNav", itemsNav(user.getRoles()));
        model.put("username", context.cookie("username"));

        context.render("dashboard.hbs", model);
    }

    public void loginAttempt(Context context){
        Usuario usuario = repositorioDeUsuario.findUsuarioByUsername(context.formParam("username"));
        String password = context.formParam("password");
     /*   String username = context.formParam("username");
        System.out.println("Nombre de usuario: " + username);
        System.out.println("Contraseña: " + password);
        System.out.println("Contraseña: " + usuario.getPassword());*/

        if(usuario != null){
            if(usuario.getPassword().equals(password)){
                context.redirect("/");
                context.cookie("id", String.valueOf(usuario.getId()));
                context.cookie("username", usuario.getUsername());

                System.out.println("id: " + context.attribute("id"));
                System.out.println("Username: " + context.attribute("username"));
            }
            else{
                context.redirect("/login");

            }
        } else{
            context.redirect("/login");

        }
    }

    public void logout(Context context){
        context.cookie("id","",0);
        context.cookie("username","",0);

        context.redirect("/login");
    }

    public String itemsNav(List<Rol> roles){
        StringBuilder navItems= new StringBuilder();
       if(contiene(roles, new Rol("admin",null))){
           navItems.append("            <hr class=\"sidebar-divider\">\n" +
                           "\n" +
                           "            <!-- Heading -->\n" +
                           "            <div class=\"sidebar-heading\">\n" +
                           "                Administrador\n" +
                           "            </div>" +
                           "<li class=\"nav-item\">\n" +
                           "                <a class=\"nav-link\" href=\"/usuarios\">\n" +
                           "                    <i class=\"fas fa-fw fa-users\"></i>\n" +
                           "                    <span>Usuarios</span></a>\n" +
                           "            </li>")
                   .append("<li class=\"nav-item\">\n")
                   .append("    <a class=\"nav-link\" href=\"/cargaEntidades\">\n")
                   .append("        <i class=\"fas fa-fw fa-building\"></i>\n")
                   .append("        <span>Entidades Prestadoras</span></a>\n")
                   .append("</li>")
                    .append("<li class=\"nav-item \">\n" +
                            "                <a class=\"nav-link\" href=\"/cargaOrganismos\">\n" +
                            "                    <i class=\"fas fa-fw fa-building\"></i>\n" +
                            "                    <span>Organismos de Control</span></a>\n" +
                            "            </li>")
                .append("<li class=\"nav-item \">\n" +
                        "                            <a class=\"nav-link\" href=\"/admin/incidentes\">\n" +
                        "                                <i class=\"fas fa-fw fa-list\"></i>\n" +
                        "                                <span>Incidentes</span></a>\n" +
                        "                        </li>");
        }
       return navItems.toString();
    }

    public static boolean contiene(List<Rol> lista1, Rol rol2) {
        for (Rol rol1 : lista1) {
                    if(rol1.getNombre().equals(rol2.getNombre())){
                        return true;
                    }
        }

        return false; // No se encontraron nombres de rol iguales
    }

}
