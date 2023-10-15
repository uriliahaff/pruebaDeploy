package domain.Repositorios;


import domain.Usuarios.EntidadPrestadora;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class RepositorioEntidadPrestadora {
    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(EntidadPrestadora entidadPrestadora) {
        entityManager.getTransaction().begin();
        entityManager.persist(entidadPrestadora);
        entityManager.getTransaction().commit();
    }

    public void update(EntidadPrestadora entidadPrestadora) {
        entityManager.getTransaction().begin();
        entityManager.merge(entidadPrestadora);
        entityManager.getTransaction().commit();
    }

    public void delete(EntidadPrestadora entidadPrestadora) {
        entityManager.getTransaction().begin();
        entityManager.remove(entidadPrestadora);
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        EntidadPrestadora entidadPrestadora = entityManager.find(EntidadPrestadora.class, id);
        if (entidadPrestadora != null) {
            entityManager.remove(entidadPrestadora);
        }
        entityManager.getTransaction().commit();
    }

    public EntidadPrestadora find(int id) {
        return entityManager.find(EntidadPrestadora.class, id);
    }

    public static List<EntidadPrestadora> findAllEntidadPrestadoraByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        TypedQuery<EntidadPrestadora> query = entityManager.createQuery(
                "SELECT u FROM EntidadPrestadora u WHERE u.id IN :ids", EntidadPrestadora.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}

