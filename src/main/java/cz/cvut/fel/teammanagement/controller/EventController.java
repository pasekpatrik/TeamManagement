package cz.cvut.fel.teammanagement.controller;

import cz.cvut.fel.teammanagement.dto.AttendanceDTO;
import cz.cvut.fel.teammanagement.dto.EventDTO;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/getEvent/{eventId}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long eventId) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new EventDTO(event));
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<Event> events = eventService.getAll();

        return ResponseEntity.ok(
                events.stream()
                .map(EventDTO::new)
                .toList());
    }

    @PreAuthorize("hasRole('COACH')")
    @PostMapping("/createEvent")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event(eventDTO);
        event = eventService.create(event);

        EventDTO response = new EventDTO(event);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('COACH')")
    @PutMapping("/updateEvent/{eventId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long eventId, @RequestBody EventDTO eventDTO) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        event.setName(eventDTO.name());
        event.setStartDate(eventDTO.startDate());
        event.setStartTime(eventDTO.startTime());
        event.setCity(eventDTO.city());
        event.setAddress(eventDTO.address());
        event.setEventType(eventDTO.eventType());

        Event updated = eventService.update(event);
        return ResponseEntity.ok(new EventDTO(updated));
    }

    @PreAuthorize("hasRole('COACH')")
    @DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        if(eventService.delete(eventId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('COACH')")
    @GetMapping("/getEventAttendances/{eventId}")
    public ResponseEntity<List<AttendanceDTO>> getEventAttendances(@PathVariable Long eventId) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        List<AttendanceDTO> attendanceDTOS = eventService.findAttendancesByEventId(eventId).stream()
                .map(AttendanceDTO::new)
                .toList();
        return ResponseEntity.ok(attendanceDTOS);
    }
}
