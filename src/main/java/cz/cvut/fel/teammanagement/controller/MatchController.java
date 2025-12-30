package cz.cvut.fel.teammanagement.controller;

import cz.cvut.fel.teammanagement.dto.AttendanceDTO;
import cz.cvut.fel.teammanagement.dto.MatchDTO;
import cz.cvut.fel.teammanagement.model.Match;
import cz.cvut.fel.teammanagement.service.MatchService;
import cz.cvut.fel.teammanagement.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("matches")
public class MatchController {
    private final MatchService matchService;
    private final TeamService teamService;

    public MatchController(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;
    }

    @GetMapping("/getEvent/{eventId}")
    public ResponseEntity<MatchDTO> getEvent(@PathVariable Long eventId) {
        Match event = matchService.getById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MatchDTO(event));
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<MatchDTO>> getAllEvents() {
        List<Match> events = matchService.getAll();

        return ResponseEntity.ok(
                events.stream()
                        .map(MatchDTO::new)
                        .toList());
    }

    @PreAuthorize("hasRole('COACH')")
    @PostMapping("/createEvent")
    public ResponseEntity<MatchDTO> createEvent(@RequestBody MatchDTO eventDTO) {
        Match event = new Match(eventDTO);
        event = matchService.create(event);

        MatchDTO response = new MatchDTO(event);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('COACH')")
    @PutMapping("/updateEvent/{eventId}")
    public ResponseEntity<MatchDTO> updateEvent(@PathVariable Long eventId, @RequestBody MatchDTO matchDTO) {
        Match match = matchService.getById(eventId);
        if (match == null) {
            return ResponseEntity.notFound().build();
        }
        match.setStartDate(matchDTO.startDate());
        match.setStartTime(matchDTO.startTime());
        match.setCity(matchDTO.city());
        match.setAddress(matchDTO.address());
        match.setTeamScore(matchDTO.teamScore());
        match.setOpponentName(matchDTO.opponentName());
        match.setOpponentPhone(matchDTO.opponentPhone());
        match.setOpponentScore(matchDTO.opponentScore());
        match.setTeam(teamService.getById(matchDTO.teamId()));

        Match updated = matchService.update(match);
        return ResponseEntity.ok(new MatchDTO(updated));
    }

    @PreAuthorize("hasRole('COACH')")
    @DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        if(matchService.delete(eventId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('COACH')")
    @GetMapping("/getEventAttendances/{eventId}")
    public ResponseEntity<List<AttendanceDTO>> getEventAttendances(@PathVariable Long eventId) {
        Match event = matchService.getById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        List<AttendanceDTO> attendanceDTOS = matchService.findAttendancesByMatchId(eventId).stream()
                .map(AttendanceDTO::new)
                .toList();
        return ResponseEntity.ok(attendanceDTOS);
    }
}
