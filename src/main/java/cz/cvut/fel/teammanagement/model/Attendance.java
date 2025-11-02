package cz.cvut.fel.teammanagement.model;

import cz.cvut.fel.teammanagement.enums.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Attendance extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType statusType;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Match match;
}
