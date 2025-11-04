package cz.cvut.fel.teammanagement.model;

import cz.cvut.fel.teammanagement.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @ManyToOne
    private Account account;
}
