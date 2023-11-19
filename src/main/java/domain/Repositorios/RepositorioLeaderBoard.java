package domain.Repositorios;

import domain.Usuarios.Comunidades.Miembro;
import domain.other.EntityManagerProvider;
import domain.rankings.Leaderboard.LeaderBoardType;
import domain.rankings.Leaderboard.Leaderboard;
import domain.rankings.Leaderboard.RankLeaderBoardUnit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class RepositorioLeaderBoard
{
    private static EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();

    public void save(Leaderboard leaderboard) {
        entityManager.getTransaction().begin();
        entityManager.persist(leaderboard);
        if (leaderboard.getRankLeaderBoardUnits() != null) {
            for (RankLeaderBoardUnit unit : leaderboard.getRankLeaderBoardUnits()) {
                // Set the parent Leaderboard for each unit
                unit.setLeaderboard(leaderboard);

                // Persist the unit
                entityManager.persist(unit);
            }
        }
        entityManager.getTransaction().commit();
        System.out.println(leaderboard.getId());
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

    public Leaderboard findLatestLeaderboardByType(LeaderBoardType type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Leaderboard> cq = cb.createQuery(Leaderboard.class);

        Root<Leaderboard> leaderboardRoot = cq.from(Leaderboard.class);
        cq.select(leaderboardRoot)
                .where(cb.equal(leaderboardRoot.get("type"), type))
                .orderBy(cb.desc(leaderboardRoot.get("creacion")));

        List<Leaderboard> result = entityManager.createQuery(cq)
                .setMaxResults(1)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

}
