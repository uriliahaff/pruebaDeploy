package testing;

import domain.Procesos.ComunidadManager;
import domain.Repositorios.*;
import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.Usuarios.Rol;
import domain.Usuarios.Usuario;
import domain.entidades.Entidad;
import domain.entidades.Establecimiento;
import domain.localizaciones.Direccion;
import domain.other.*;
import domain.services.georef.entities.Localidad;
import domain.services.georef.entities.Municipio;
import domain.services.georef.entities.Provincia;
import domain.servicios.Servicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class PersistenceTest {
    private EntityManagerFactory emf;
    private EntityManager entityManager;
    @BeforeEach
    public void init() {
        //emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        //entityManager = emf.createEntityManager()
        entityManager = EntityManagerProvider.getInstance().getEntityManager();
    }
    @Test
    public void tryingToConnect()
    {
        boolean test = true;
        Assertions.assertTrue(test);
    }
    @Test
    public void testAgregarEstablecimiento() {
        // Crear una nueva instancia de Establecimiento
        Establecimiento nuevoEstablecimiento = new Establecimiento("Nombre 12", "Descripcion", new Direccion(new Provincia("loc1"),new Municipio("loc2"),new Localidad("loc3")));

        // Persistir la entidad
       // entityManager.persist(nuevoEstablecimiento);
        //entityManager.getTransaction().commit();
        RepositorioEstablecimiento repositorioEstablecimiento = new RepositorioEstablecimiento();
        repositorioEstablecimiento.save(nuevoEstablecimiento);
        // Buscar por ID
        Establecimiento encontrado = repositorioEstablecimiento.find(nuevoEstablecimiento.getId());//entityManager.find(Establecimiento.class,nuevoEstablecimiento.getId());

        // Verificar que los datos son correctos
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(nuevoEstablecimiento.getNombre(), encontrado.getNombre());
        Assertions.assertEquals(nuevoEstablecimiento.getDescripcion(), encontrado.getDescripcion());
    }
    @Test
    public void testAgregarEstablecimientoconDireccion() {
        //entityManager.getTransaction().begin();
        Localidad loc1 = new Localidad("loc 1");
        Localidad loc2 = new Localidad("loc 2");
        Municipio mun = new Municipio("mun1");
        Provincia prov = new Provincia("prov1");
        Direccion direccion1 = new Direccion(prov,mun,loc1);
        Direccion direccion2 = new Direccion(prov,mun,loc2);
        // Crear una nueva instancia de Establecimiento
        Establecimiento nuevoEstablecimiento1 = new Establecimiento(
                "Nombre 12"
                , "Descripcion"
                , direccion1);
        Establecimiento nuevoEstablecimiento2 = new Establecimiento(
                "Nombre 12"
                , "Descripcion"
                , direccion2);
        RepositorioEstablecimiento repositorioEstablecimiento = new RepositorioEstablecimiento();
        repositorioEstablecimiento.save(nuevoEstablecimiento1);
        repositorioEstablecimiento.save(nuevoEstablecimiento2);
        // Persistir la entidad
        //entityManager.persist(nuevoEstablecimiento1);
        //entityManager.persist(nuevoEstablecimiento2);
        //entityManager.getTransaction().commit();
        // Buscar por ID
        Establecimiento encontrado1 = repositorioEstablecimiento.find(nuevoEstablecimiento1.getId());//entityManager.find(Establecimiento.class,nuevoEstablecimiento1.getId());
        Establecimiento encontrado2 =  repositorioEstablecimiento.find(nuevoEstablecimiento2.getId());//entityManager.find(Establecimiento.class,nuevoEstablecimiento2.getId());

        // Verificar que los datos son correctos
        Assertions.assertNotNull(encontrado1);
        Assertions.assertNotNull(encontrado2);
        Assertions.assertEquals(encontrado1.getDireccion().getMunicipio().getId(), encontrado2.getDireccion().getMunicipio().getId());
        Assertions.assertNotEquals(encontrado1.getDireccion().getLocalidad().getId(), encontrado2.getDireccion().getLocalidad().getId());
    }
    @Test
    public void testOrganismoDeControl()
    {
        //entityManager.getTransaction().begin();
        Rol rol1 = new Rol("Admin", new ArrayList<>());
        Rol rol2 = new Rol("SuperAdmin", new ArrayList<>());
        OrganismoDeControl organismo = new OrganismoDeControl(
                "usuario",
                "totallyValidPasswordWithNumbersLike142AndSymbolsLike!!!",
                "correo@ejemplo.com",
                "Nombre del Organismo",
                "Descripción del Organismo"
        );
        organismo.getUsuario().addRol(rol1);
        organismo.getUsuario().addRol(rol2);
        Servicio servicio = new Servicio("servicio","servicioMock");
        organismo.setServicio(servicio);
        new RepositorioUsuario().saveOrganismoDeControl(organismo);
       // entityManager.persist(organismo);
        //entityManager.getTransaction().commit();

        OrganismoDeControl org = entityManager.find(OrganismoDeControl.class,organismo.getId());
        Assertions.assertEquals(org.getUsuario().getRoles().size(),2);

    }
    @Test
    public void testSuperMiembro()
    {

        Miembro miembro = new Miembro(
                "miembroAdmin",
                "admini",
                "correo",
                "numeros",
                null,
                new Usuario("miembroAdmin","aaa")
                );
        miembro.getUsuario().addRol(new RepositorioRol().findRolByNombre("Admin"));
        miembro.getUsuario().addRol(new RepositorioRol().findRolByNombre("SuperAdmin"));
        new RepositorioUsuario().saveMiembro(miembro);
        // entityManager.persist(organismo);
        //entityManager.getTransaction().commit();

        Miembro miembroRecuperado = entityManager.find(Miembro.class,miembro.getId());
        Assertions.assertEquals(miembroRecuperado.getUsuario().getRoles().size(),2);

    }

    @Test
    public void testEliminarUsuario() {

        OrganismoDeControl organismo = new OrganismoDeControl(

                "usuarion",
                "totallyValidPasswordWithNumbersLike142AndSymbolsLike!!!",
                "correo@ejemplo.com",
                "Nombre del Organismo",
                "Descripción del Organismo"
        );
        Servicio servicio = new Servicio("servicio","servicioMock");
        organismo.setServicio(servicio);

        new RepositorioUsuario().saveUsuario(organismo.getUsuario());

        new RepositorioUsuario().saveOrganismoDeControl(organismo);
        int id = organismo.getId();
        System.out.println(organismo.getUsuario().getId());
        System.out.println(id);
        System.out.println(organismo.getUsuario().getUsername());

        Usuario usuarioRecuperado = new RepositorioUsuario().findUsuarioById(organismo.getUsuario().getId());//entityManager.find(Usuario.class, organismo.getId());
        Assertions.assertNotNull(usuarioRecuperado);
        Assertions.assertEquals("usuarion", usuarioRecuperado.getUsername());

        new RepositorioUsuario().delete(usuarioRecuperado);

        OrganismoDeControl usuarioEliminado = new RepositorioUsuario().findOrganismoDeControlById(organismo.getId());//entityManager.find(OrganismoDeControl.class, id);
        Assertions.assertNull(usuarioEliminado);
    }

    @Test
    public void generateComunity()
    {
        Comunidad com = new Comunidad("Comunidad con miembros", 2);
        Miembro miembro = new Miembro(
                "miembro con com"
                ,"a"
                , "correo",
                "telefono",
                null,
                new Usuario("username", "contraseña")
                );
        Miembro miembro2 = new Miembro(
                "miembro con com 2"
                ,"a"
                , "correo",
                "telefono",
                null,
                new Usuario("username", "contraseña")
        );

        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
        repositorioUsuario.saveMiembro(miembro);
        repositorioUsuario.saveMiembro(miembro2);
        RepositorioComunidad repositorioComunidad = new RepositorioComunidad();
        repositorioComunidad.save(com);
        ComunidadManager.agregarMiembro(miembro,com);
        ComunidadManager.agregarMiembro(miembro2,com);
    }


    @Test
    public void testBuscarUsuario() {
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();
        OrganismoDeControl organismo = new OrganismoDeControl(

                "usuarion123455",
                "totallyValidPasswordWithNumbersLike142AndSymbolsLike!!!",
                "correo@ejemplo.com",
                "Nombre del Organismo",
                "Descripción del Organismo"
        );
        OrganismoDeControl organismo2 = new OrganismoDeControl(

                "usuarion12345",
                "totallyValidPasswordWithNumbersLike142AndSymbolsLike!!!",
                "correo@ejemplo.com",
                "Nombre del Organismo",
                "Descripción del Organismo"
        );
        OrganismoDeControl organismo3 = new OrganismoDeControl(

                "usuarion1234555",
                "totallyValidPasswordWithNumbersLike142AndSymbolsLike!!!",
                "correo@ejemplo.com",
                "Nombre del Organismo",
                "Descripción del Organismo"
        );
        String nombre = organismo.getUsuario().getUsername();
        Servicio servicio = new Servicio("servicio","servicioMock");
        organismo.setServicio(servicio);
        organismo2.setServicio(servicio);
        organismo3.setServicio(servicio);

        repositorioUsuario.saveOrganismoDeControl(organismo);
        repositorioUsuario.saveOrganismoDeControl(organismo2);
        repositorioUsuario.saveOrganismoDeControl(organismo3);



        Usuario usuarioRecuperado = repositorioUsuario.findUsuarioByUsername(nombre);//entityManager.find(Usuario.class, organismo.getId());

        Assertions.assertNotNull(usuarioRecuperado);
        Assertions.assertEquals("usuarion123455", usuarioRecuperado.getUsername());
        //Assertions.assertEquals("usuarion123455", usuarioRecuperado.getUsername());
        //Aca quiero checkear que no encuentre nombres parecidos
        repositorioUsuario.delete(usuarioRecuperado);

    }
    @Test
    public void testRecuperarODC()
    {
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario();

        Servicio servicio = new Servicio("Serv", "sasdf");
        OrganismoDeControl organismo = new OrganismoDeControl(

                "usuarion123455",
                "totallyValidPasswordWithNumbersLike142AndSymbolsLike!!!",
                "correo@ejemplo.com",
                "Nombre del Organismo",
                "Descripción del Organismo"
        );
        organismo.setServicio(servicio);
        repositorioUsuario.saveOrganismoDeControl(organismo);

        OrganismoDeControl organismoDeControlRecuperado = repositorioUsuario.findOrganismoDeControlById(organismo.getId());
        Assertions.assertEquals(organismoDeControlRecuperado.getUsuario().getUsername(), organismo.getUsuario().getUsername());

        Assertions.assertNotNull(organismoDeControlRecuperado.getServicio());


    }



    @Test
    public void testFindEstablecimientosByDireccion() {

        entityManager.getTransaction().begin();
        RepositorioEstablecimiento repositorioEstablecimiento = new RepositorioEstablecimiento();
        // Crear Provincias
        Provincia caba = new Provincia("CABA");
        entityManager.persist(caba);

        // Crear Municipios
        Municipio palermo = new Municipio("Palermo");
        entityManager.persist(palermo);

        Municipio almagro = new Municipio("Almagro");
        entityManager.persist(almagro);

        // Crear Localidades
        Localidad martin = new Localidad("Martin");
        entityManager.persist(martin);

        // Crear Direcciones
        Direccion direccion1 = new Direccion(caba, palermo, martin);
        entityManager.persist(direccion1);

        Direccion direccion2 = new Direccion(caba, palermo);
        entityManager.persist(direccion2);

        Direccion direccion3 = new Direccion(caba, almagro);
        entityManager.persist(direccion3);
        entityManager.getTransaction().commit();

        // Crear Establecimientos
        Establecimiento establecimiento1 = new Establecimiento("Establecimiento1", "Descripcion1", direccion1);
        repositorioEstablecimiento.save(establecimiento1);

        Establecimiento establecimiento2 = new Establecimiento("Establecimiento2", "Descripcion2", direccion2);
        repositorioEstablecimiento.save(establecimiento2);

        Establecimiento establecimiento3 = new Establecimiento("Establecimiento3", "Descripcion3", direccion3);
        repositorioEstablecimiento.save(establecimiento3);
        // Ahora llamamos a tu método para buscar establecimientos
        List<Establecimiento> result = repositorioEstablecimiento.findEstablecimientosByDireccion(caba, palermo, martin);

        // Verificamos que el resultado es el esperado
        Assertions.assertEquals(2, result.size());  // Debería devolver dos establecimientos: "Establecimiento1" y "Establecimiento2"

        // Asegurar que los establecimientos correctos se devuelvan (puedes hacer esto de varias maneras, aquí hay una)
        Assertions.assertTrue(result.stream().anyMatch(est -> est.getNombre().equals("Establecimiento1")));
        Assertions.assertTrue(result.stream().anyMatch(est -> est.getNombre().equals("Establecimiento2")));
    }

    @Test
    public void getEntidadesPrestadoras()
    {
        Entidad entidad = new Entidad("entidad","hola","rth","dfg");
        new RepositorioEntidad().save(entidad);
        Usuario usu = new Usuario("Cosa","sdfjlin");
        new RepositorioUsuario().saveUsuario(usu);
        EntidadPrestadora entidadPrestadora = new EntidadPrestadora(
                entidad
                , usu
                ,"efdg"
                ,"efwd",
                "dsfv");
        new RepositorioUsuario().saveEntidadPrestadora(entidadPrestadora);
    }
}
