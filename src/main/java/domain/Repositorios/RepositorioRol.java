package domain.Repositorios;

import domain.Procesos.EntidadesManager;
import domain.Usuarios.Permiso;
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

    public Rol findRolById(int id)
    {
        return entityManager.find(Rol.class, id);
    }
    public Permiso findPermisoById(int id)
    {
        return entityManager.find(Permiso.class, id);
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
    public void save(Permiso permiso) {
        entityManager.getTransaction().begin();
        entityManager.persist(permiso);
        entityManager.getTransaction().commit();
    }

    public void update(Permiso permiso) {
        entityManager.getTransaction().begin();
        entityManager.merge(permiso);
        entityManager.getTransaction().commit();
    }

    public void delete(Permiso permiso) {
        entityManager.getTransaction().begin();
        entityManager.remove(permiso);
        entityManager.getTransaction().commit();
    }


    public Permiso findPermisoByNombre(String nombre) {
        try {
            String jpql = "SELECT r FROM Rol r WHERE r.descripcion = :descripcion";
            return entityManager.createQuery(jpql, Permiso.class)
                    .setParameter("descripcion", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Puedes manejar la excepción de la forma que mejor te parezca
        }
    }


}
