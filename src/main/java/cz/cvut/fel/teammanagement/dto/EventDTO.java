package cz.cvut.fel.teammanagement.dto;

import cz.cvut.fel.teammanagement.enums.EventType;
import cz.cvut.fel.teammanagement.model.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventDTO(
        Long id,
        String name,
        LocalDate startDate,
        LocalTime startTime,
        String city,
        String address,
        EventType eventType) {

    public EventDTO(Event event) {
        this(event.getId(),
                event.getName(),
                event.getStartDate(),
                event.getStartTime(),
                event.getCity(),
                event.getAddress(),
                event.getEventType());
    }
}
