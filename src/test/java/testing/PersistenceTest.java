package testing;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.persistence.*;
import java.util.ArrayList;


public class PersistenceTest {
    private EntityManagerFactory emf;
    private EntityManager entityManager;
    @BeforeEach
    public void init() {
        emf = Persistence.createEntityManagerFactory("simple-persistence-unit");
        entityManager = emf.createEntityManager();
    }
    @Test
    public void tryingToConnect()
    {
        boolean test = true;
        Assertions.assertTrue(test);
    }
    @Test
    public void testAgregarEstablecimiento() {
        entityManager.getTransaction().begin();
        // Crear una nueva instancia de Establecimiento
        Establecimiento nuevoEstablecimiento = new Establecimiento("Nombre 12", "Descripcion", new Direccion(new Provincia("loc1"),new Municipio("loc2"),new Localidad("loc3")));

        // Persistir la entidad
        entityManager.persist(nuevoEstablecimiento);
        entityManager.getTransaction().commit();
        // Buscar por ID
        Establecimiento encontrado = entityManager.find(Establecimiento.class,nuevoEstablecimiento.getId());

        // Verificar que los datos son correctos
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(nuevoEstablecimiento.getNombre(), encontrado.getNombre());
        Assertions.assertEquals(nuevoEstablecimiento.getDescripcion(), encontrado.getDescripcion());
    }@Test
    public void testAgregarEstablecimientoconDireccion() {
        entityManager.getTransaction().begin();
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

        // Persistir la entidad
        entityManager.persist(nuevoEstablecimiento1);
        entityManager.persist(nuevoEstablecimiento2);
        entityManager.getTransaction().commit();
        // Buscar por ID
        Establecimiento encontrado1 = entityManager.find(Establecimiento.class,nuevoEstablecimiento1.getId());
        Establecimiento encontrado2 = entityManager.find(Establecimiento.class,nuevoEstablecimiento2.getId());

        // Verificar que los datos son correctos
        Assertions.assertNotNull(encontrado1);
        Assertions.assertNotNull(encontrado2);
        Assertions.assertEquals(encontrado1.getDireccion().getMunicipio().getId(), encontrado2.getDireccion().getMunicipio().getId());
        Assertions.assertNotEquals(encontrado1.getDireccion().getLocalidad().getId(), encontrado2.getDireccion().getLocalidad().getId());
    }
    @Test
    public void testOrganismoDeControl()
    {
        entityManager.getTransaction().begin();
        Rol rol1 = new Rol("Admin", new ArrayList<>());
        Rol rol2 = new Rol("SuperAdmin", new ArrayList<>());
        OrganismoDeControl organismo = new OrganismoDeControl(
                "usuario",
                "totallyValidPasswordWithNumbersLike142AndSymbolsLike!!!",
                "correo@ejemplo.com",
                "Nombre del Organismo",
                "Descripción del Organismo"
        );
        organismo.addRol(rol1);
        organismo.addRol(rol2);
        entityManager.persist(organismo);
        entityManager.getTransaction().commit();

        OrganismoDeControl org = entityManager.find(OrganismoDeControl.class,organismo.getId());
        Assertions.assertEquals(org.getRoles().size(),2);

    }

    @Test
    public void testEliminarUsuario() {
        entityManager.getTransaction().begin();

        OrganismoDeControl organismo = new OrganismoDeControl(

                "usuarion",
                "totallyValidPasswordWithNumbersLike142AndSymbolsLike!!!",
                "correo@ejemplo.com",
                "Nombre del Organismo",
                "Descripción del Organismo"
        );
        int id = organismo.getId();


        entityManager.persist(organismo);

        entityManager.getTransaction().commit();

        Usuario usuarioRecuperado = entityManager.find(Usuario.class, organismo.getId());
        Assertions.assertNotNull(usuarioRecuperado);
        Assertions.assertEquals("usuarion", usuarioRecuperado.getUsername());

        entityManager.getTransaction().begin();
        entityManager.remove(usuarioRecuperado);
        entityManager.getTransaction().commit();

        OrganismoDeControl usuarioEliminado = entityManager.find(OrganismoDeControl.class, id);
        Assertions.assertNull(usuarioEliminado);
    }
}
