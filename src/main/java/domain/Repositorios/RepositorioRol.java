package domain.Repositorios;

import domain.Procesos.EntidadesManager;
import domain.Usuarios.Rol;
import domain.other.EntityManagerProvider;
import domain.servicios.Servicio;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class RepositorioRol
{
    private EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(Rol rol) {
        entityManager.getTransaction().begin();
        entityManager.persist(rol);
        entityManager.getTransaction().commit();
    }

    public void update(Rol rol) {
        entityManager.getTransaction().begin();
        entityManager.merge(rol);
        entityManager.getTransaction().commit();
    }

    public void delete(Rol rol) {
        entityManager.getTransaction().begin();
        entityManager.remove(rol);
        entityManager.getTransaction().commit();
    }


    public Rol findRolByNombre(String nombre) {
        try {
            String jpql = "SELECT r FROM Rol r WHERE r.nombre = :nombre";
            return entityManager.createQuery(jpql, Rol.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Puedes manejar la excepción de la forma que mejor te parezca
        }
    }


}
