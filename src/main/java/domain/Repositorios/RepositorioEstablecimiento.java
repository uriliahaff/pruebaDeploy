package domain.Repositorios;

import domain.Usuarios.Comunidades.Miembro;
import domain.entidades.Establecimiento;
import domain.localizaciones.Direccion;
import domain.other.EntityManagerProvider;
import domain.services.georef.entities.*;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.List;

public class RepositorioEstablecimiento {

    private static EntityManager entityManager= EntityManagerProvider.getInstance().getEntityManager();

    public RepositorioEstablecimiento()
    {
        //this.entityManager= EntityManagerProvider.getInstance().getEntityManager();
    }

    public void save(Establecimiento establecimiento) {
        entityManager.getTransaction().begin();
        entityManager.persist(establecimiento);
        entityManager.getTransaction().commit();
    }
    public Establecimiento find(int ID)
    {
        return entityManager.find(Establecimiento.class,ID);
    }

    public void update(Establecimiento establecimiento) {
        entityManager.getTransaction().begin();
        entityManager.merge(establecimiento);
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        Establecimiento establecimiento = entityManager.find(Establecimiento.class, id);
        if (establecimiento != null) {
            entityManager.remove(establecimiento);
        }
        entityManager.getTransaction().commit();
    }

    public List<Establecimiento> findEstablecimientosByDireccion(Provincia provincia, Municipio municipio, Localidad localidad) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Establecimiento> query = cb.createQuery(Establecimiento.class);
        Root<Establecimiento> establecimientoRoot = query.from(Establecimiento.class);
        Join<Establecimiento, Direccion> direccionJoin = establecimientoRoot.join("direccion");

        Predicate provinciaPredicate = cb.equal(direccionJoin.get("provincia"), provincia);

        Predicate municipioPredicate = cb.or(
                cb.isNull(direccionJoin.get("municipio")),
                cb.equal(direccionJoin.get("municipio"), municipio),
                cb.isNull(cb.literal(municipio))
        );

        Predicate localidadPredicate = cb.or(
                cb.isNull(direccionJoin.get("localidad")),
                cb.equal(direccionJoin.get("localidad"), localidad),
                cb.isNull(cb.literal(localidad))
        );

        query.select(establecimientoRoot)
                .where(cb.and(provinciaPredicate, municipioPredicate, localidadPredicate));

        TypedQuery<Establecimiento> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public static List<Establecimiento> findEstablecimientoByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        TypedQuery<Establecimiento> query = entityManager.createQuery(
                "SELECT u FROM Establecimiento u WHERE u.id IN :ids", Establecimiento.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }

}
