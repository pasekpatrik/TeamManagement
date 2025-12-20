package cz.cvut.fel.teammanagement.controller;

import cz.cvut.fel.teammanagement.dto.AttendanceDTO;
import cz.cvut.fel.teammanagement.dto.EventDTO;
import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.model.Match;
import cz.cvut.fel.teammanagement.service.AccountService;
import cz.cvut.fel.teammanagement.service.AttendanceService;
import cz.cvut.fel.teammanagement.service.EventService;
import cz.cvut.fel.teammanagement.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final EventService eventService;
    private final AccountService accountService;
    private final MatchService matchService;

    public AttendanceController(AttendanceService attendanceService, EventService eventService, AccountService accountService, MatchService matchService) {
        this.attendanceService = attendanceService;
        this.eventService = eventService;
        this.accountService = accountService;
        this.matchService = matchService;
    }

    @GetMapping("/registerEventAttendance/{eventId}/{accountId}/{statusType}")
    public ResponseEntity<Void> registerEventAttendance(@PathVariable Long eventId, @PathVariable Long accountId, @PathVariable StatusType statusType) {
        Event event = eventService.getById(eventId);
        Account account = accountService.getById(accountId);
        if (event == null || account == null) {
            return ResponseEntity.notFound().build();
        }

         if(attendanceService.registerAttendance(account, event, statusType)) {
             return ResponseEntity.ok().build();
         }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/registerMatchAttendance/{matchId}/{accountId}/{statusType}")
    public ResponseEntity<Void> registerMatchAttendance(@PathVariable Long matchId, @PathVariable Long accountId, @PathVariable StatusType statusType) {
        Match match = matchService.getById(matchId);
        Account account  = accountService.getById(accountId);
        if (match == null || account == null) {
            return ResponseEntity.notFound().build();
        }

        if(attendanceService.registerAttendance(account, match, statusType)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
