package cz.cvut.fel.teammanagement.model;

import cz.cvut.fel.teammanagement.dto.AccountDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends AbstractEntity {

    public Account (AccountDTO accountDTO) {
        this.firstName = accountDTO.firstName();
        this.lastName = accountDTO.lastName();
        this.email = accountDTO.email();
        this.phone = accountDTO.phone();
        this.birthday = accountDTO.birthday();
    }

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @ManyToMany(mappedBy = "accounts")
    private List<Team> teams;

    @OneToMany(mappedBy = "account")
    private List<Role> roles;

    @OneToMany(mappedBy = "account")
    private List<Attendance> attendances = new ArrayList<>();

    public void setAttendances(List<Attendance> attendances) {
        this.attendances.clear();
        if (attendances != null) {
            this.attendances.addAll(attendances);
        }
    }

    public void addAttandance(Attendance attendance) {
        attendances.add(attendance);
    }
}

