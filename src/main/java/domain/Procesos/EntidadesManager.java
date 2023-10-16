package domain.Procesos;

import domain.Repositorios.RepositorioEntidad;
import domain.entidades.Entidad;
import domain.entidades.Establecimiento;
import domain.entidades.TipoEntidad;
import domain.localizaciones.Direccion;

public class EntidadesManager
{
    private static RepositorioEntidad repositorioEntidad = new RepositorioEntidad();
    public static Entidad crearEntidad(String nombre, TipoEntidad tipoEntidad, String email, String descripcion)
    {
        Entidad nuevaEntidad = new Entidad(nombre, tipoEntidad, email, descripcion);
        repositorioEntidad.save(nuevaEntidad);
        return nuevaEntidad;

    }
    public static void agregarEstablecimientosAEntidad(Entidad entidad, String nombre, String descripcion, Direccion direccion)
    {
        Establecimiento nuevoEstablecimiento = new Establecimiento(nombre, descripcion, direccion);

        entidad.agregarEstablecimiento(nuevoEstablecimiento);

        repositorioEntidad.update(entidad);
    }
    public static void agregarEstablecimientosAEntidad(Entidad entidad, Establecimiento ...establecimiento)
    {
        for (Establecimiento est:establecimiento
             ) {
            entidad.agregarEstablecimiento(est);
        }
        repositorioEntidad.update(entidad);
    }
}
