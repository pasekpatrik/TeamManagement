package cz.cvut.fel.teammanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class File extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate uploadDate;
}
