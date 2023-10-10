package domain.Procesos;

import domain.Repositorios.RepositorioEstablecimiento;
import domain.entidades.Establecimiento;
import domain.servicios.Estado;
import domain.servicios.PrestacionDeServicio;
import domain.servicios.Servicio;

public class EstablecimientoManager
{
    private static RepositorioEstablecimiento repositorioEstablecimiento = new RepositorioEstablecimiento();
    public static void nuevaPrestacionDeServicio(Establecimiento establecimiento, Servicio servicio)
    {
        PrestacionDeServicio nuevaPrestacionDeServicio = new PrestacionDeServicio(servicio, establecimiento, Estado.IN_SERVICE);
        establecimiento.agregarServicio(nuevaPrestacionDeServicio);
        repositorioEstablecimiento.update(establecimiento);
    }
}
