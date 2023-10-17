package testing;

import domain.Repositorios.RepositorioComunidad;
import domain.Repositorios.RepositorioEstablecimiento;
import domain.Repositorios.RepositorioServicio;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.Usuario;
import domain.entidades.Entidad;
import domain.entidades.Establecimiento;
import domain.informes.Incidente;
import domain.localizaciones.Direccion;
import domain.services.georef.entities.Municipio;
import domain.services.georef.entities.Provincia;
import domain.servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class RankingsTest {


    private Miembro miembro1, miembro2,miembro3,miembro4;
    private Usuario usuario1, usuario2, usuario3, usuario4;
    private Establecimiento establecimiento1, establecimiento2, establecimiento3;
    private Entidad entidad1, entidad2,entidad3;
    private Incidente incidente1, incidente2, incidente3, incidente4, incidente5;
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

        establecimiento1 = new Establecimiento("establecimiento1","estacion bulnes", new Direccion(new Provincia("CABA"), new Municipio("Palermo")));
        establecimiento2 = new Establecimiento("establecimiento2", "estacion scalabrini", new Direccion(new Provincia("CABA"), new Municipio("Palermo")));
        establecimiento3 = new Establecimiento("establecimiento3", "estacion chacarita", new Direccion(new Provincia("CABA"),new Municipio("Almagro")));

        RepositorioEstablecimiento repositorioEstablecimiento = new RepositorioEstablecimiento();
        repositorioEstablecimiento.save(establecimiento1);
        repositorioEstablecimiento.save(establecimiento2);
        repositorioEstablecimiento.save(establecimiento3);



    }

    @Test
    public void rankingTest1(){

    }
}
