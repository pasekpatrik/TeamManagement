package cz.cvut.fel.teammanagement.controller;

import cz.cvut.fel.teammanagement.dto.TeamDTO;
import cz.cvut.fel.teammanagement.model.Team;
import cz.cvut.fel.teammanagement.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllTeams")
    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamService.getAll();

        return teams.stream()
                .map(team -> new TeamDTO(team.getId(), team.getName(), team.getCity(), team.getSportType()))
                .toList();
    }

    @GetMapping("/getTeam/{id}")
    public ResponseEntity<TeamDTO> getTeam(@PathVariable long id) {
        Team team = teamService.getById(id);
        if (team == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new TeamDTO(team));
    }

    @PostMapping("/createTeam")
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
        Team team = new Team(teamDTO);
        Team saved = teamService.create(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TeamDTO(saved));
    }

    @PutMapping("/updateTeam/{teamId}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long teamId, @RequestBody TeamDTO teamDTO) {
        Team team = teamService.getById(teamId);
        if (team == null) {
            return ResponseEntity.notFound().build();
        }
        team.setName(teamDTO.name());
        team.setCity(teamDTO.city());
        team.setSportType(teamDTO.sportType());

        Team update = teamService.update(team);
        return ResponseEntity.ok(new TeamDTO(update));
    }

    @DeleteMapping("/deleteTeam/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        if (teamService.delete(teamId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/addMemberToTeam/{teamId}/account/{accountId}")
    public ResponseEntity<TeamDTO> addMemberToTeam(@PathVariable Long teamId,
                                                   @PathVariable Long accountId) {
        teamService.addMemberToTeam(teamId, accountId);
        Team updated = teamService.getById(teamId);
        return ResponseEntity.ok(new TeamDTO(updated));
    }
}
