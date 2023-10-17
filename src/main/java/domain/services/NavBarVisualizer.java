package domain.services;

import domain.Usuarios.Rol;

import java.util.List;

public class NavBarVisualizer {


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
