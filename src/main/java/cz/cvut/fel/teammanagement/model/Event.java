package cz.cvut.fel.teammanagement.model;

import cz.cvut.fel.teammanagement.dto.EventDTO;
import cz.cvut.fel.teammanagement.enums.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event extends AbstractEntity {

    public Event(EventDTO eventDTO) {
        this.name = eventDTO.name();
        this.startDate = eventDTO.startDate();
        this.startTime = eventDTO.startTime();
        this.City = eventDTO.city();
        this.address = eventDTO.address();
        this.eventType = eventDTO.eventType();
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "city", nullable = false)
    private String City;

    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "event")
    private List<Attendance> attendances = new ArrayList<>();

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
