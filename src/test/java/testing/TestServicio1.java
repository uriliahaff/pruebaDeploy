package testing;

import domain.Repositorios.RepositorioComunidad;
import domain.Repositorios.RepositorioServicio;
import domain.Repositorios.RepositorioUsuario;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.ConfiguracionNotificacionDeIncidentes;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.Usuario;
import domain.services.service1.*;
import domain.services.service1.model.ComunidadModel;
import domain.services.service1.model.EstablecimientoModel;
import domain.services.service1.model.PeticionModel;
import domain.services.service1.model.PropuestaDeFusionModel;
import domain.servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestServicio1
{
    private Miembro miembro1, miembro2,miembro3,miembro4;
    private Usuario usuario1, usuario2, usuario3, usuario4;

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
        comunidad1.agregarInteres(servicio3);
        comunidad1.agregarInteres(servicio4);

        comunidad2.agregarInteres(servicio1);
        comunidad2.agregarInteres(servicio2);
        comunidad2.agregarInteres(servicio3);
        comunidad2.agregarInteres(servicio4);

        comunidad1.agregarMiembros(miembro1, miembro2, miembro3, miembro4);
        comunidad2.agregarMiembros(miembro1, miembro2, miembro3, miembro4);

        RepositorioComunidad repositorioComunidad = new RepositorioComunidad();
        repositorioComunidad.save(comunidad1);
        repositorioComunidad.save(comunidad2);
    }

    @Test
    public void callSugerenciasTest() throws IOException
    {


        ComunidadModel comunidadModel1 = new ComunidadModel(comunidad1);
        ComunidadModel comunidadModel2 = new ComunidadModel(comunidad2);

        EstablecimientoModel establecimientoModelMock1 = new EstablecimientoModel(1L, "mock1");
        List<EstablecimientoModel> list1 = new ArrayList<EstablecimientoModel>(){
            {
                add(establecimientoModelMock1);
            }
        };
        List<EstablecimientoModel> list2 = new ArrayList<EstablecimientoModel>(){
        {
            add(establecimientoModelMock1);
        }
    };
        comunidadModel1.establecimientosObservados=list1;
        comunidadModel2.establecimientosObservados=list2;

        List<ComunidadModel> listComMod = new ArrayList<>();
        listComMod.add(comunidadModel1);
        listComMod.add(comunidadModel2);


        PeticionModel peticionModel = new PeticionModel(new ArrayList<PropuestaDeFusionModel>(),listComMod);


        ComunidadApiService comunidadServiceApi = ComunidadApiService.getInstancia();
        List<PropuestaDeFusionModel> propuestas =  comunidadServiceApi.obtenerPropuestas(peticionModel);
        Assertions.assertNotNull(propuestas);

        System.out.println("propuestas "+ propuestas.get(0).getFechaPropuesta().toString());
    }

    @Test
    public void callApiSugerirComunidad() throws IOException
    {
        List<Comunidad> comunidades = new ArrayList<>();
        comunidades.add(comunidad1);
        System.out.println(comunidad1.getId());
        comunidades.add(comunidad2);
        ComunidadFusion comunidadFusion = new ComunidadFusionAdapter();
        List<PropuestaDeFusion> propuestaDeFusions = new ArrayList<>();
        List<PropuestaDeFusion> propuestas =  comunidadFusion.getSugerenciasDeFusion(comunidades,propuestaDeFusions);
        Assertions.assertNotNull(propuestas);
    }

    @Test
    public void callFusionarComunidades() throws IOException
    {
        List<Comunidad> comunidades = new ArrayList<>();
        comunidades.add(comunidad1);
        System.out.println(comunidad1.getId());
        comunidades.add(comunidad2);
        ComunidadFusion comunidadFusion = new ComunidadFusionAdapter();
        List<PropuestaDeFusion> propuestaDeFusions = new ArrayList<>();
        List<PropuestaDeFusion> propuestas =  comunidadFusion.getSugerenciasDeFusion(comunidades,propuestaDeFusions);

        Comunidad comunidadFusion1 = comunidadFusion.fusionarComunidades(propuestas.get(0));
        Assertions.assertEquals(comunidad1.getNombre()+" - "+comunidad2.getNombre(), comunidadFusion1.getNombre());
    }


}
