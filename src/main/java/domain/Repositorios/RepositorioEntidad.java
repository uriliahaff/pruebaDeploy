package domain.Repositorios;

import domain.Procesos.EntidadesManager;
import domain.Usuarios.Usuario;
import domain.Usuarios.Comunidades.Miembro;

import domain.entidades.Entidad;
import domain.entidades.Establecimiento;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class RepositorioEntidad
{
    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(Entidad entidad) {
        entityManager.getTransaction().begin();
        entityManager.persist(entidad);
        entityManager.getTransaction().commit();
    }

    public void update(Entidad entidad) {
        entityManager.getTransaction().begin();
        entityManager.merge(entidad);
        entityManager.getTransaction().commit();
    }

    public void delete(Entidad entidad) {
    entityManager.getTransaction().begin();
    entityManager.remove(entidad);
    entityManager.getTransaction().commit();
}

    public void delete(int id) {
        entityManager.getTransaction().begin();
        Entidad entidad = entityManager.find(Entidad.class, id);
        if (entidad != null) {
            entityManager.remove(entidad);
        }
        entityManager.getTransaction().commit();
    }

    public Entidad findEntidadesById(int id) {
        return entityManager.find(Entidad.class, id);
    }
    public static List<Entidad> findAllEntidadByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        TypedQuery<Entidad> query = entityManager.createQuery(
                "SELECT u FROM Entidad u WHERE u.id IN :ids", Entidad.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    public Entidad findEntidadById(int id) {
        return entityManager.find(Entidad.class, id);
    }
}
