package cz.cvut.fel.teammanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity @Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToMany(mappedBy = "users")
    private List<Team> teams;
}
