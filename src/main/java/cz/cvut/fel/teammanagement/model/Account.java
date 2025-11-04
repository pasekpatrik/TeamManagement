package cz.cvut.fel.teammanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends AbstractEntity {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToMany(mappedBy = "accounts")
    private List<Team> teams;

    @OneToMany(mappedBy = "account")
    private List<Role> roles;

    @OneToMany(mappedBy = "account")
    private List<Attendance> attendances;
}
