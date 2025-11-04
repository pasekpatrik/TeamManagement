package cz.cvut.fel.teammanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Match extends AbstractEntity {
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "team_score", nullable = false)
    private int teamScore;

    @Column(name = "opponent_name", nullable = false)
    private String opponentName;

    @Column(name = "opponent_phone", nullable = false)
    private String opponentPhone;

    @Column(name = "opponent_score", nullable = false)
    private int opponentScore;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "match")
    private List<Attendance> attendances;
}
