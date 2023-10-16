package domain.Repositorios;

import domain.entidades.Establecimiento;
import domain.informes.Incidente;
import domain.localizaciones.Direccion;
import domain.other.EntityManagerProvider;
import domain.services.georef.entities.*;
import domain.servicios.PrestacionDeServicio;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class RepositorioIncidente {

    @PersistenceContext
    private EntityManager entityManager;

    public RepositorioIncidente() {
        this.entityManager = EntityManagerProvider.getInstance().getEntityManager();
    }

    public void save(Incidente incidente) {
        entityManager.getTransaction().begin();
        entityManager.persist(incidente);
        entityManager.getTransaction().commit();
    }

    public void update(Incidente incidente) {
        entityManager.getTransaction().begin();
        entityManager.merge(incidente);
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        Incidente incidente = entityManager.find(Incidente.class, id);
        if (incidente != null) {
            entityManager.remove(incidente);
        }
        entityManager.getTransaction().commit();
    }

    public Incidente findById(int id) {
        return entityManager.find(Incidente.class, id);
    }

    public List<Incidente> findAll() {
        return entityManager.createQuery("from Incidente", Incidente.class).getResultList();
    }
    public List<Incidente> findIncidentesByDireccion(Provincia provincia, Municipio municipio, Localidad localidad) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Incidente> query = cb.createQuery(Incidente.class);
        Root<Incidente> incidenteRoot = query.from(Incidente.class);

        // Joins múltiples para acceder a la dirección
        Join<Incidente, PrestacionDeServicio> prestacionJoin = incidenteRoot.join("prestacionDeServicio");
        Join<PrestacionDeServicio, Establecimiento> establecimientoJoin = prestacionJoin.join("establecimiento");
        Join<Establecimiento, Direccion> direccionJoin = establecimientoJoin.join("direccion");

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

        query.select(incidenteRoot)
                .where(cb.and(provinciaPredicate, municipioPredicate, localidadPredicate));

        TypedQuery<Incidente> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
    public List<Incidente> findClosedLastWeek() {
        LocalDate today = LocalDate.now();
        LocalDate lastWeekStart = today.minusDays(7);

        String hql = "FROM Incidente i WHERE i.fechaCierre BETWEEN :lastWeekStart AND :today";

        return entityManager.createQuery(hql, Incidente.class)
                .setParameter("lastWeekStart", lastWeekStart)
                .setParameter("today", today)
                .getResultList();
    }


}
