package testing;

import domain.Procesos.ComunidadManager;
import domain.Procesos.EntidadesManager;
import domain.Procesos.EstablecimientoManager;
import domain.Repositorios.*;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.Usuario;
import domain.entidades.Entidad;
import domain.entidades.Establecimiento;
import domain.entidades.TipoEntidad;
import domain.informes.Incidente;
import domain.localizaciones.Direccion;
import domain.services.georef.entities.Localidad;
import domain.services.georef.entities.Municipio;
import domain.services.georef.entities.Provincia;
import domain.services.service2.CalculadorConfianzaAPI;
import domain.services.service2.CalculadorConfianzaAPIService;
import domain.services.service2.CalculadorConfianzaAdapter;
import domain.services.service2.CambioDePuntaje;
import domain.servicios.PrestacionDeServicio;
import domain.servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TestServicio2
{
    private Comunidad comunidad1;
    @Test
    public void testGetGradoDeConfianza() throws IOException {

        RepositorioEstablecimiento repositorioEstablecimiento = new RepositorioEstablecimiento();
        // Crear Provincias
        Provincia caba = new Provincia("CABA");

        // Crear Municipios
        Municipio palermo = new Municipio("Palermo");

        Municipio almagro = new Municipio("Almagro");

        // Crear Localidades
        Localidad martin = new Localidad("Martin");

        // Crear Direcciones
        Direccion direccion1 = new Direccion(caba, palermo, martin);

        Direccion direccion2 = new Direccion(caba, palermo);

        Direccion direccion3 = new Direccion(caba, almagro);


        comunidad1 = new Comunidad("com 4",0);
        //String nombre, String apellido, String correoElectronico, String telefono,
        // ConfiguracionNotificacionDeIncidentes configuracionNotificacionDeIncidentes,
        // Usuario usuario
        Miembro miembro1 = new Miembro(
                "miem1"
                , "miems"
                ,"correo"
                ,"numero"
                ,null
                ,new Usuario("cuenta1","passwordsegura123")
        );

        new RepositorioUsuario().saveMiembro(miembro1);
        new RepositorioComunidad().save(comunidad1);
        ComunidadManager.agregarMiembro(miembro1,comunidad1);
        Servicio servicio = new Servicio("serv1", "muy servicioso");
        TipoEntidad tipoEntidad = new TipoEntidad("Tren");
        Entidad entidad = EntidadesManager.crearEntidad(
                "entidad1"
                ,tipoEntidad
                ,"mail"
                ,"descripcion real"
        );
        EntidadesManager.agregarEstablecimientosAEntidad(
                entidad
        , "estacion1"
        ,"estacionada"
        ,direccion1);
        new RepositorioServicio().save(servicio);
        EstablecimientoManager.nuevaPrestacionDeServicio(entidad.getEstablecimientos().get(0), servicio);

        Incidente incidente = new Incidente(
                "algo paso"
                , miembro1
                , entidad.getEstablecimientos().get(0).getServicios().get(0)
                , LocalDateTime.now());
        incidente.cerrarIncidente(LocalDateTime.now().plus(1, ChronoUnit.MINUTES),miembro1);
        Incidente incidente2 = new Incidente(
                "algo paso"
                , miembro1
                , entidad.getEstablecimientos().get(0).getServicios().get(0)
                , LocalDateTime.now().plusDays(2));
        incidente2.cerrarIncidente(LocalDateTime.now().plusDays(2).plus(1, ChronoUnit.MINUTES),miembro1);
        new RepositorioIncidente().save(incidente);

        List<Incidente> incidentes = new ArrayList<>();
        incidentes.add(incidente);
        incidentes.add(incidente2);
        List<CambioDePuntaje>  cambioDePuntajes= new ArrayList<>();


        CalculadorConfianzaAdapter calculadorConfianzaAdapter =CalculadorConfianzaAdapter.getInstance();
        cambioDePuntajes = calculadorConfianzaAdapter.obternerCambios(incidentes);



        Assertions.assertEquals(-0.2, cambioDePuntajes.get(0).getCambio());
        Assertions.assertEquals(miembro1.getUsuario().getId(), cambioDePuntajes.get(0).getUsuario().getId());
        cambioDePuntajes.forEach(CambioDePuntaje::apply);

                /*
                new Entidad(
                "entidad1"
                ,"Tren"
                ,"mail"
                ,"descripcion real"
        );
        Establecimiento establecimiento = new Establecimiento()
        PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(servicio, )
        Incidente incidente = new Incidente("paso algo", miembro1, )*/
    }

    @Test
    public void testActualizarComunidadValues() throws IOException {
        CalculadorConfianzaAdapter calculadorConfianzaAdapter = CalculadorConfianzaAdapter.getInstance();
        List<Comunidad> comunidades = new ArrayList<>();
        comunidades.add(new RepositorioComunidad().findByName("com 4"));
        System.out.println("Comunidad: "+comunidades.get(0).getMiembros().get(0).getUsuario().getGradoDeConfianza());
        System.out.println(comunidades.get(0).getMiembros().stream().map(Miembro::getUsuario).mapToDouble(Usuario::getGradoDeConfianza).sum());
        calculadorConfianzaAdapter.actualizarGradoConfianzaDeComunidades(comunidades);
        Assertions.assertEquals(-1,comunidades.get(0).getGradoDeConfianza());
    }
}
