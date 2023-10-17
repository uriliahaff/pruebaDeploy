package domain.rankings.Leaderboard;

import domain.Repositorios.RepositorioEntidad;
import domain.entidades.Entidad;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rank_leaderboard")
public class RankLeaderBoardUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leaderboard_id")
    private Leaderboard leaderboard;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entidad_id")
    private Entidad entidad;

    @Column(name = "value")
    private double value;

    @Column(name = "date_time")
    private LocalDate dateTime;

    public RankLeaderBoardUnit(Integer entidad_id, double value) {
        this.entidad = new RepositorioEntidad().findEntidadById(entidad_id);
        this.value = value;
        dateTime = LocalDate.now();
    }
}
