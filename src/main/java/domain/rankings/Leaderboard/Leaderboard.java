package domain.rankings.Leaderboard;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "leaderboard")
public class Leaderboard
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "leaderboard")
    private List<RankLeaderBoardUnit> rankLeaderBoardUnits;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    LeaderBoardType type;

    @Column(name = "creado")
    private LocalDateTime creacion;

    public Leaderboard(){}
    public Leaderboard(List<RankLeaderBoardUnit> rankLeaderBoardUnits, LeaderBoardType type)
    {
        this.rankLeaderBoardUnits = new ArrayList<>();
        this.rankLeaderBoardUnits.addAll(rankLeaderBoardUnits);
        this.type=type;
        this.creacion = LocalDateTime.now();
    }
    public List<RankLeaderBoardUnit> getRankLeaderBoardUnits() {
        return rankLeaderBoardUnits;
    }

    public int getId() {
        return id;
    }
}
