package cz.cvut.fel.teammanagement.model;

import cz.cvut.fel.teammanagement.enums.SportType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Team extends AbstractEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SportType sportType;

    @ManyToMany
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
