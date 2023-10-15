package domain.Repositorios;

import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.entidades.Entidad;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class RepositorioComunidad
{
    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(Comunidad comunidad) {
        entityManager.getTransaction().begin();
        entityManager.persist(comunidad);
        entityManager.getTransaction().commit();
    }

    public void update(Comunidad comunidad) {
        entityManager.getTransaction().begin();
        entityManager.merge(comunidad);
        entityManager.getTransaction().commit();
    }

    public void delete(Comunidad comunidad) {
        entityManager.getTransaction().begin();
        entityManager.remove(comunidad);
        entityManager.getTransaction().commit();
    }
    public static List<Comunidad> findComunidadByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        TypedQuery<Comunidad> query = entityManager.createQuery(
                "SELECT u FROM Comunidad u WHERE u.id IN :ids", Comunidad.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
