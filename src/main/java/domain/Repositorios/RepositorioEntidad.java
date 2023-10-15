package domain.Repositorios;

import domain.Procesos.EntidadesManager;
import domain.Usuarios.Usuario;
import domain.entidades.Entidad;
import domain.entidades.Establecimiento;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;

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

    public void delete(int id) {
        entityManager.getTransaction().begin();
        Entidad entidad = entityManager.find(Entidad.class, id);
        if (entidad != null) {
            entityManager.remove(entidad);
        }
        entityManager.getTransaction().commit();
    }

    public Entidad findEntidadById(int id) {
        return entityManager.find(Entidad.class, id);
    }


}
