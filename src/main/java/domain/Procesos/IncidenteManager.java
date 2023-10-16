package domain.Procesos;

import domain.Repositorios.RepositorioIncidente;
import domain.Usuarios.Comunidades.Miembro;
import domain.informes.Incidente;
import domain.services.notificadorDeIncidentes.NotificadorDeIncidentes;
import domain.servicios.PrestacionDeServicio;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IncidenteManager
{
    private static RepositorioIncidente repositorioIncidente = new RepositorioIncidente();
    public static Incidente abrirIncidente(String descripcion, Miembro miembroInformante, PrestacionDeServicio servicioAfectado, LocalDateTime fechaInicio)
    {
        Incidente nuevoIncidente = new Incidente(descripcion,miembroInformante, servicioAfectado, fechaInicio);
        NotificadorDeIncidentes
                .notificarIncidente(
                        nuevoIncidente
                );
        repositorioIncidente.save(nuevoIncidente);
        return nuevoIncidente;
    }

    public static void cerrarIncidente(Incidente incidente, Miembro miembroAnalizador)
    {
        incidente.cerrarIncidente(LocalDateTime.now(), miembroAnalizador);
        repositorioIncidente.update(incidente);
        NotificadorDeIncidentes.notificarIncidente(incidente);
    }
}
