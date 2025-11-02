package cz.cvut.fel.teammanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int teamScore;

    @Column(nullable = false)
    private String opponentName;

    @Column(nullable = false)
    private String opponentPhone;

    @Column(nullable = false)
    private int opponentScore;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "match")
    private List<Attendance> attendances;
}
