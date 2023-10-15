package domain.Repositorios;

import domain.Usuarios.Comunidades.Miembro;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;


public class RepositorioMiembro {
    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(Miembro miembro) {
        entityManager.getTransaction().begin();
        entityManager.persist(miembro);
        entityManager.getTransaction().commit();
    }

    public void update(Miembro miembro) {
        entityManager.getTransaction().begin();
        entityManager.merge(miembro);
        entityManager.getTransaction().commit();
    }

    public void delete(Miembro miembro) {
        entityManager.getTransaction().begin();
        entityManager.remove(miembro);
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        Miembro miembro = entityManager.find(Miembro.class, id);
        if (miembro != null) {
            entityManager.remove(miembro);
        }
        entityManager.getTransaction().commit();
    }

    public Miembro find(int id) {
        return entityManager.find(Miembro.class, id);
    }

    public List<Miembro> findAllMiembroByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        TypedQuery<Miembro> query = entityManager.createQuery(
                "SELECT m FROM Miembro m WHERE m.id IN :ids", Miembro.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
