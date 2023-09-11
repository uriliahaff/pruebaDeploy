package domain.services.notificadorDeIncidentes;

import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.informes.Incidente;
import domain.servicios.PrestacionDeServicio;

import java.util.List;
import java.util.stream.Collectors;

public class NotificadorDeIncidentes {


    public static void notificarIncidente(Incidente incidente){

        PrestacionDeServicio servicioAfectado = incidente.getServicioAfectado();
        //List<Comunidad> comunidades = RepositorioComunidades.getInstance().obtenerComunidades();
/*
        List<Miembro> miembrosUnicos = comunidades.stream()
                .filter(comunidad -> comunidad.deInteres(servicioAfectado.getServicio()))
                .flatMap(comunidad -> comunidad.miembrosFiltradosPorInteresEnLocalizacion(servicioAfectado.getLocalizacion()).stream())
                .distinct()
                .collect(Collectors.toList());
*/
        List<Miembro> miembrosUnicos = incidente.getComunidadesAfectadas().stream()
                //.filter(comunidad -> comunidad.deInteres(servicioAfectado.getServicio()))//TODO: Abrir solo para las communidades que le interesa?
                .flatMap(comunidad -> comunidad.miembrosFiltradosPorInteresEnLocalizacion(servicioAfectado.getLocalizacion()).stream())
                .distinct()
                .collect(Collectors.toList());
        miembrosUnicos.forEach(
                        miembro -> miembro.getCommandoNotificar(incidente).notificarIncidente()
                );
    }

}
