package cz.cvut.fel.teammanagement.model;

import cz.cvut.fel.teammanagement.dto.MatchDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Match extends AbstractEntity {

    public Match(MatchDTO matchDTO) {
        this.startDate = matchDTO.startDate();
        this.startTime = matchDTO.startTime();
        this.city = matchDTO.city();
        this.address = matchDTO.address();
        this.teamScore = matchDTO.teamScore();
        this.opponentName = matchDTO.opponentName();
        this.opponentPhone = matchDTO.opponentPhone();
        this.opponentScore = matchDTO.opponentScore();
    }

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

    public void setAttendances(List<Attendance> attendances) {
        this.attendances.clear();
        if (attendances != null) {
            this.attendances.addAll(attendances);
        }
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }
}
