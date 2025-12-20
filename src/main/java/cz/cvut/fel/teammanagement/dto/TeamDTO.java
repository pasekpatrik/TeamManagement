package cz.cvut.fel.teammanagement.dto;

import cz.cvut.fel.teammanagement.enums.SportType;
import cz.cvut.fel.teammanagement.model.Team;

public record TeamDTO(Long id, String name, String city, SportType sportType) {

    public TeamDTO (Team team) {
        this(team.getId(), team.getName(), team.getCity(), team.getSportType());
    }
}
