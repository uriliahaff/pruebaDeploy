package domain.services;


import domain.Usuarios.Rol;

import java.util.List;

public class MyHbsHelper{

    // Helper para verificar si un rol está presente en la lista de roles del usuario
    public static String tieneRol(List<Rol> rolesUsuario, int roleId) {
        for (Rol rol : rolesUsuario) {
            if (rol.getId() == roleId) {
                return "checked";
            }
        }
        return "";
    }

}