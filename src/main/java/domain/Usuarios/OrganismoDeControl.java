package domain.Usuarios;

import domain.entidades.Entidad;

public class OrganismoDeControl extends Usuario{
    private int id;
    private Entidad entidad;
    private Usuario usuario;
    private String correoElectronicoResponsable;
    private String nombre;
    private String descripcion;
}
