package domain.Repositorios;

import domain.Usuarios.Comunidades.Comunidad;
import domain.Usuarios.Comunidades.Miembro;
import domain.entidades.Entidad;
import domain.other.EntityManagerProvider;
import domain.services.service1.ComunidadFusionAdapter;
import domain.services.service1.PropuestaDeFusion;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.IOException;
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

    public Comunidad find(int id) {
        return entityManager.find(Comunidad.class, id);
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

    public Comunidad findByName(String nombre) {
        String jpql = "FROM Comunidad WHERE nombre = :nombreParam";
        TypedQuery<Comunidad> query = entityManager.createQuery(jpql, Comunidad.class);
        query.setParameter("nombreParam", nombre);

        List<Comunidad> resultados = query.getResultList();

        // Si hay resultados, retorna el primero. Si no, retorna null.
        return resultados.isEmpty() ? null : resultados.get(0);
    }


}
