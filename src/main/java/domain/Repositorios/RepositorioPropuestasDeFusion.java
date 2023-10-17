package domain.Repositorios;

import domain.entidades.Establecimiento;
import domain.other.EntityManagerProvider;
import domain.services.service1.PropuestaDeFusion;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepositorioPropuestasDeFusion
{
    EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
    public void save(PropuestaDeFusion propuestaDeFusion) {
        entityManager.getTransaction().begin();
        entityManager.persist(propuestaDeFusion);
        entityManager.getTransaction().commit();
    }
    public void saveAll(List<PropuestaDeFusion> propuestasDeFusion) {
        entityManager.getTransaction().begin();
        for (PropuestaDeFusion propuesta : propuestasDeFusion) {
            entityManager.persist(propuesta);
        }
        entityManager.getTransaction().commit();
    }
    public PropuestaDeFusion find(int ID)
    {
        return entityManager.find(PropuestaDeFusion.class,ID);
    }

    public void update(PropuestaDeFusion propuestaDeFusion) {
        entityManager.getTransaction().begin();
        entityManager.merge(propuestaDeFusion);
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        PropuestaDeFusion propuestaDeFusion = entityManager.find(PropuestaDeFusion.class, id);
        if (propuestaDeFusion != null) {
            entityManager.remove(propuestaDeFusion);
        }
        entityManager.getTransaction().commit();
    }


    public List<PropuestaDeFusion> findAll() {
    TypedQuery<PropuestaDeFusion> query = entityManager.createQuery(
            "SELECT p FROM PropuestaDeFusion p", PropuestaDeFusion.class);
    return query.getResultList();
    }

}
