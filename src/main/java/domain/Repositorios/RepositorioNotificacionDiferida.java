package domain.Repositorios;

import domain.informes.NotificacionDiferida;
import domain.other.EntityManagerProvider;

import javax.persistence.EntityManager;

public class RepositorioNotificacionDiferida
{
    EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(NotificacionDiferida notificacionDiferida)
    {
        //if (!entityManager.getTransaction().isActive())
        entityManager.getTransaction().begin();
        entityManager.persist(notificacionDiferida);
        entityManager.getTransaction().commit();
    }
}
