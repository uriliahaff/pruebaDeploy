package controllers;

import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Permiso;
import domain.Usuarios.Rol;
import io.javalin.http.Context;

import java.util.List;

public class RolController
{
    private RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
    public void indexRols(Context context)
    {
        List<Rol> roles = repositorioUsuario.buscarTodosRoles();
        List<Permiso> permisos = repositorioUsuario.buscarTodosPermisos();


    }
}
