package domain.Repositorios;

import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioEntidadPrestadoraOrganismoControl {

    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public List<EntidadPrestadora> buscarTodosEntidades() {
        return entityManager.createQuery("SELECT e FROM EntidadPrestadora e", EntidadPrestadora.class).getResultList();
    }

    public List<OrganismoDeControl> buscarTodosOrganismos() {
        return entityManager.createQuery("SELECT o FROM OrganismoDeControl o", OrganismoDeControl.class).getResultList();
    }

    public void guardarEntidadesPrestadoras(List<EntidadPrestadora> entidades){
        for (EntidadPrestadora entidad : entidades) {
            saveEntity(entidad);
        }
    }
    public void guardarOrganismosControl(List<OrganismoDeControl> organismos){
        for (OrganismoDeControl organismo : organismos) {
            saveEntity(organismo);
        }
    }


    private <T> void saveEntity(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }


}
