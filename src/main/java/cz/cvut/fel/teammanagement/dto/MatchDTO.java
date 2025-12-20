package cz.cvut.fel.teammanagement.dto;

import cz.cvut.fel.teammanagement.model.Match;

import java.time.LocalDate;
import java.time.LocalTime;

public record MatchDTO(
    Long id,
    LocalDate startDate,
    LocalTime startTime,
    String city,
    String address,
    int teamScore,
    String opponentName,
    String opponentPhone,
    int opponentScore,
    Long teamId
) {
    public MatchDTO(Match match) {
        this(
            match.getId(),
            match.getStartDate(),
            match.getStartTime(),
            match.getCity(),
            match.getAddress(),
            match.getTeamScore(),
            match.getOpponentName(),
            match.getOpponentPhone(),
            match.getOpponentScore(),
            match.getTeam() != null ? match.getTeam().getId() : null
        );
    }
}
