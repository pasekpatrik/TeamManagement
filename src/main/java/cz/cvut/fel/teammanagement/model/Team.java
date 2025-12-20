package cz.cvut.fel.teammanagement.model;

import cz.cvut.fel.teammanagement.dto.TeamDTO;
import cz.cvut.fel.teammanagement.enums.SportType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Team extends AbstractEntity {

    public Team (TeamDTO teamDTO) {
        this.name = teamDTO.name();
        this.city = teamDTO.city();
        this.sportType = teamDTO.sportType();
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "sport_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SportType sportType;

    @ManyToMany
    @JoinTable(name = "account_team",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> accounts;

    @OneToMany
    private List<Event> events;

    @OneToMany
    private List<Match> matches;

    @OneToMany
    private List<Document> documents;

    @OneToMany
    private List<Image> images;
}
