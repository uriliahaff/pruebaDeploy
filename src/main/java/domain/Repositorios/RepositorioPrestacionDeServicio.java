package domain.Repositorios;

import domain.Usuarios.Rol;
import domain.other.EntityManagerProvider;
import domain.servicios.PrestacionDeServicio;

import javax.persistence.EntityManager;

public class RepositorioPrestacionDeServicio
{
    private EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(PrestacionDeServicio prestacionDeServicio) {
        entityManager.getTransaction().begin();
        entityManager.persist(prestacionDeServicio);
        entityManager.getTransaction().commit();
    }

    public void update(PrestacionDeServicio prestacionDeServicio) {
        entityManager.getTransaction().begin();
        entityManager.merge(prestacionDeServicio);
        entityManager.getTransaction().commit();
    }

    public void delete(PrestacionDeServicio prestacionDeServicio) {
        entityManager.getTransaction().begin();
        entityManager.remove(prestacionDeServicio);
        entityManager.getTransaction().commit();
    }
}
