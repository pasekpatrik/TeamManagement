package cz.cvut.fel.teammanagement.controller;

import cz.cvut.fel.teammanagement.dto.EventDTO;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{eventId}")
    public EventDTO getEvent(@PathVariable Long eventId) {
        Event event = eventService.getById(eventId);
        return new EventDTO(event.getId(),
                event.getName(),
                event.getStartDate(),
                event.getStartTime(),
                event.getCity(),
                event.getAddress(),
                event.getEventType());
    }

    @GetMapping
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventService.getAll();

        return events.stream()
                .map(event -> new EventDTO(event.getId(),
                        event.getName(),
                        event.getStartDate(),
                        event.getStartTime(),
                        event.getCity(),
                        event.getAddress(),
                        event.getEventType())
                )
                .toList();
    }

    @PreAuthorize("hasRole('COACH')")
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.name());
        event.setStartDate(eventDTO.startDate());
        event.setStartTime(eventDTO.startTime());
        event.setCity(eventDTO.city());
        event.setAddress(eventDTO.address());
        event.setEventType(eventDTO.eventType());

        Event saved = eventService.create(event);

        EventDTO response = new EventDTO(saved.getId(),
                saved.getName(),
                saved.getStartDate(),
                saved.getStartTime(),
                saved.getCity(),
                saved.getAddress(),
                saved.getEventType());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('COACH')")
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long eventId, @RequestBody EventDTO eventDTO) {
        Event event = eventService.getById(eventId);
        event.setName(eventDTO.name());
        event.setStartDate(eventDTO.startDate());
        event.setStartTime(eventDTO.startTime());
        event.setCity(eventDTO.city());
        event.setAddress(eventDTO.address());
        event.setEventType(eventDTO.eventType());

        Event updated = eventService.update(event);

        EventDTO response = new EventDTO(updated.getId(),
                updated.getName(),
                updated.getStartDate(),
                updated.getStartTime(),
                updated.getCity(),
                updated.getAddress(),
                updated.getEventType());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('COACH')")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.delete(eventId);
        return ResponseEntity.noContent().build();
    }
}
