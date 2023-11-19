package testing;

import domain.Repositorios.*;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.Usuario;
import domain.entidades.Entidad;
import domain.entidades.Establecimiento;
import domain.informes.Incidente;
import domain.localizaciones.Direccion;
import domain.rankings.Leaderboard.Leaderboard;
import domain.rankings.RankingMayorCantidadIncidentesReportados;
import domain.rankings.RankingMayorPromedioTiempoCierreIncidentes;
import domain.services.georef.entities.Municipio;
import domain.services.georef.entities.Provincia;
import domain.servicios.Estado;
import domain.servicios.PrestacionDeServicio;
import domain.servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RankingsTest {


    private Miembro miembro1, miembro2,miembro3,miembro4;
    private Usuario usuario1, usuario2, usuario3, usuario4;
    private Establecimiento establecimiento1, establecimiento2, establecimiento3;
    private Entidad entidad1, entidad2,entidad3;
    private PrestacionDeServicio prestacion1, prestacion2, prestacion3, prestacion4;
    private Incidente incidente1, incidente2, incidente3, incidente4;
    private Comunidad comunidad1, comunidad2;


    @BeforeEach
    void setUp() {
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

        usuario1 = new Usuario("username1", "password1");
        repositorioUsuario.saveUsuario(usuario1);
        miembro1 = new Miembro("Juan", "Pérez", "juan.perez@email.com", "555-1234", null, usuario1);
        repositorioUsuario.saveMiembro(miembro1);

        usuario2 = new Usuario("username2", "password2");
        repositorioUsuario.saveUsuario(usuario2);
        miembro2 = new Miembro("Maria", "García", "maria.garcia@email.com", "555-5678", null, usuario2);
        repositorioUsuario.saveMiembro(miembro2);

        usuario3 = new Usuario("username3", "password3");
        repositorioUsuario.saveUsuario(usuario3);
        miembro3 = new Miembro("Carlos", "Lopez", "carlos.lopez@email.com", "555-9876", null, usuario3);
        repositorioUsuario.saveMiembro(miembro3);


        usuario4 = new Usuario("username4", "password4");
        repositorioUsuario.saveUsuario(usuario4);
        miembro4 = new Miembro("Ana", "Torres", "ana.torres@email.com", "555-5432", null, usuario4);
        repositorioUsuario.saveMiembro(miembro4);

        Servicio servicio1 = new Servicio("escalera","sube y baja");
        Servicio servicio2 = new Servicio("escalera 2 electric boogaloo","sube y baja con estilo");
        Servicio servicio3 = new Servicio("escalera3","sube y baja");
        Servicio servicio4 = new Servicio("escalera4","sube y baja");

        RepositorioServicio repositorioServicio = new RepositorioServicio();
        repositorioServicio.save(servicio1);
        repositorioServicio.save(servicio2);
        repositorioServicio.save(servicio3);
        repositorioServicio.save(servicio4);

        comunidad1 = new Comunidad("Com1",2);
        comunidad2 = new Comunidad("Com2",2);
        comunidad1.agregarInteres(servicio1);
        comunidad1.agregarInteres(servicio2);

        comunidad2.agregarInteres(servicio3);
        comunidad2.agregarInteres(servicio4);

        comunidad1.agregarMiembros(miembro1);
        comunidad2.agregarMiembros(miembro2, miembro3, miembro4);

        RepositorioComunidad repositorioComunidad = new RepositorioComunidad();
        repositorioComunidad.save(comunidad1);
        repositorioComunidad.save(comunidad2);

        entidad1 = new Entidad("entidad1", "estacionDeTren","entidad1@email.com","tren" );
        entidad2 = new Entidad("entidad2", "estacionDeSubte", "ent2@email.com","subte");
        entidad3 = new Entidad("entidad3", "estacionDeSubte", "ent3@email.com", "subte");

        RepositorioEntidad repositorioEntidad = new RepositorioEntidad();

        repositorioEntidad.save(entidad1);
        repositorioEntidad.save(entidad2);
        repositorioEntidad.save(entidad3);


        establecimiento1 = new Establecimiento("establecimiento1","estacion bulnes", new Direccion(new Provincia("CABA"), new Municipio("Palermo")));
        establecimiento2 = new Establecimiento("establecimiento2", "estacion scalabrini", new Direccion(new Provincia("CABA"), new Municipio("Palermo")));
        establecimiento3 = new Establecimiento("establecimiento3", "estacion chacarita", new Direccion(new Provincia("CABA"),new Municipio("Almagro")));

        establecimiento1.setEntidad(entidad1);
        establecimiento2.setEntidad(entidad2);
        establecimiento3.setEntidad(entidad3);


        RepositorioEstablecimiento repositorioEstablecimiento = new RepositorioEstablecimiento();
        repositorioEstablecimiento.save(establecimiento1);
        repositorioEstablecimiento.save(establecimiento2);
        repositorioEstablecimiento.save(establecimiento3);

        System.out.println(establecimiento1.getId()+": "+establecimiento1.getEntidad());
        prestacion1 = new PrestacionDeServicio(servicio1,establecimiento1, Estado.OUT_OF_SERVICE );
        prestacion2= new PrestacionDeServicio(servicio2,establecimiento2,Estado.OUT_OF_SERVICE);
        prestacion3= new PrestacionDeServicio(servicio3,establecimiento3,Estado.OUT_OF_SERVICE);
        prestacion4=new PrestacionDeServicio(servicio4,establecimiento1,Estado.OUT_OF_SERVICE);

        RepositorioPrestacionDeServicio repositorioPrestacionDeServicio = new RepositorioPrestacionDeServicio();
        repositorioPrestacionDeServicio.save(prestacion1);
        repositorioPrestacionDeServicio.save(prestacion2);
        repositorioPrestacionDeServicio.save(prestacion3);
        repositorioPrestacionDeServicio.save(prestacion4);


        incidente1 = new Incidente("se rompio",miembro1,prestacion1, LocalDateTime.now().minusHours(3));
        incidente2 = new Incidente("se rompio",miembro2,prestacion2, LocalDateTime.now().minusDays(2));
        incidente3 = new Incidente("se rompio",miembro3,prestacion3, LocalDateTime.now().minusDays(1));
        incidente4 = new Incidente("se rompio",miembro4,prestacion4, LocalDateTime.now().minusHours(7));

        incidente1.cerrarIncidente(LocalDateTime.now().minusHours(1),miembro1);
        incidente2.cerrarIncidente(LocalDateTime.now().minusHours(24),miembro2);
        incidente3.cerrarIncidente(LocalDateTime.now().minusHours(20),miembro3);
        incidente4.cerrarIncidente(LocalDateTime.now(),miembro4);

        RepositorioIncidente repositorioIncidente= new RepositorioIncidente();
        repositorioIncidente.save(incidente1);
        repositorioIncidente.save(incidente2);
        repositorioIncidente.save(incidente3);
        repositorioIncidente.save(incidente4);

    }

    @Test
    public void rankingTest1()
    {
        List<Incidente> incidentes = new ArrayList<Incidente>();
        incidentes.add(incidente1);
        incidentes.add(incidente2);
        incidentes.add(incidente3);
        incidentes.add(incidente4);

        Leaderboard leaderbord = new RankingMayorCantidadIncidentesReportados().generarRanking();
        Assertions.assertTrue(leaderbord.getRankLeaderBoardUnits().size()>0);

    }
    @Test
    public void rankingTest2()
    {
        Leaderboard leaderbord = new RankingMayorPromedioTiempoCierreIncidentes().generarRanking();
        Assertions.assertTrue(leaderbord.getRankLeaderBoardUnits().size()>0);

    }
}
