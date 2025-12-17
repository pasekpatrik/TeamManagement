package cz.cvut.fel.teammanagement.dto;

import cz.cvut.fel.teammanagement.enums.EventType;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventDTO(
        Long id,
        String name,
        LocalDate startDate,
        LocalTime startTime,
        String city,
        String address,
        EventType eventType) {}
