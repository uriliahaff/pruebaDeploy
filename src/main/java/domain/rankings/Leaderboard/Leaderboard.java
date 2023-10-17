package domain.rankings.Leaderboard;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "leaderboard")
public class Leaderboard
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "leaderboard")
    private List<RankLeaderBoardUnit> rankLeaderBoardUnits = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    LeaderBoardType type;

    public Leaderboard(){}
    public Leaderboard(List<RankLeaderBoardUnit> rankLeaderBoardUnits, LeaderBoardType type)
    {
        this.rankLeaderBoardUnits.addAll(rankLeaderBoardUnits);
        this.type=type;
    }
    public List<RankLeaderBoardUnit> getRankLeaderBoardUnits() {
        return rankLeaderBoardUnits;
    }

}
