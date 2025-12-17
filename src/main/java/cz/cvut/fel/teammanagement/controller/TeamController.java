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
    @GetMapping
    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamService.getAll();

        return teams.stream()
                .map(team -> new TeamDTO(team.getId(), team.getName(), team.getCity(), team.getSportType()))
                .toList();
    }

    @GetMapping("/{id}")
    public TeamDTO getTeam(@PathVariable long id) {
        Team team = teamService.getById(id);
        return new TeamDTO(team.getId(), team.getName(), team.getCity(), team.getSportType());
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.name());
        team.setCity(teamDTO.city());
        team.setSportType(teamDTO.sportType());

        Team saved = teamService.create(team);

        TeamDTO response = new TeamDTO(saved.getId(), saved.getName(), saved.getCity(), saved.getSportType());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long teamId, @RequestBody TeamDTO teamDTO) {
        Team team = teamService.getById(teamId);
        team.setName(teamDTO.name());
        team.setCity(teamDTO.city());
        team.setSportType(teamDTO.sportType());

        Team update = teamService.update(team);
        TeamDTO response = new TeamDTO(update.getId(), update.getName(), update.getCity(), update.getSportType());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.delete(teamId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{teamId}/account/{accountId}")
    public ResponseEntity<TeamDTO> addMemberToTeam(@PathVariable Long teamId,
                                                   @PathVariable Long accountId) {
        teamService.addMemberToTeam(teamId, accountId);
        Team updated = teamService.getById(teamId);
        TeamDTO response = new TeamDTO(updated.getId(), updated.getName(), updated.getCity(), updated.getSportType());
        return ResponseEntity.ok(response);
    }
}
