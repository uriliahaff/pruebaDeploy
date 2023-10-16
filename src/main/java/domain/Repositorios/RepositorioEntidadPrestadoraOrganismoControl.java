package domain.Repositorios;

import domain.Usuarios.EntidadPrestadora;
import domain.Usuarios.OrganismoDeControl;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioEntidadPrestadoraOrganismoControl {

    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public List buscarTodosEntidades() {
        return entityManager.createQuery("from " + EntidadPrestadora.class.getName()).getResultList();
    }

    public List buscarTodosOrganismos() {
        return entityManager.createQuery("from " + OrganismoDeControl.class.getName()).getResultList();
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
