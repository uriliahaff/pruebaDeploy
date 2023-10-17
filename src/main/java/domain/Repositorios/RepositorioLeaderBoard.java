package domain.Repositorios;

import domain.Usuarios.Comunidades.Miembro;
import domain.other.EntityManagerProvider;
import domain.rankings.Leaderboard.Leaderboard;

import javax.persistence.EntityManager;

public class RepositorioLeaderBoard
{
    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(Leaderboard leaderboard) {
        entityManager.getTransaction().begin();
        entityManager.persist(leaderboard);
        entityManager.getTransaction().commit();
    }

    public void update(Leaderboard leaderboard) {
        entityManager.getTransaction().begin();
        entityManager.merge(leaderboard);
        entityManager.getTransaction().commit();
    }

    public void delete(Leaderboard leaderboard) {
        entityManager.getTransaction().begin();
        entityManager.remove(leaderboard);
        entityManager.getTransaction().commit();
    }

}
