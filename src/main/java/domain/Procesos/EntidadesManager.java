package domain.Procesos;

import domain.Repositorios.RepositorioEntidad;
import domain.entidades.Entidad;
import domain.entidades.Establecimiento;
import domain.entidades.TipoEntidad;
import domain.localizaciones.Direccion;

public class EntidadesManager
{
    private static RepositorioEntidad repositorioEntidad = new RepositorioEntidad();
    public void crearEntidad(String nombre, String tipo, TipoEntidad tipoEntidad, String email, String descripcion)
    {
        Entidad nuevaEntidad = new Entidad(nombre, tipo, email, descripcion);

    }
    public void agregarEstablecimientosAEntidad(Entidad entidad, String nombre, String descripcion, Direccion direccion)
    {
        Establecimiento nuevoEstablecimiento = new Establecimiento(nombre, descripcion, direccion);

        entidad.agregarEstablecimiento(nuevoEstablecimiento);

        repositorioEntidad.update(entidad);
    }
    public void agregarEstablecimientosAEntidad(Entidad entidad, Establecimiento ...establecimiento)
    {
        for (Establecimiento est:establecimiento
             ) {
            entidad.agregarEstablecimiento(est);
        }
        repositorioEntidad.update(entidad);
    }
}
