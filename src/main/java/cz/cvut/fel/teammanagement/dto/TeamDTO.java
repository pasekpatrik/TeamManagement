package cz.cvut.fel.teammanagement.dto;

import cz.cvut.fel.teammanagement.enums.SportType;

public record TeamDTO(Long id, String name, String city, SportType sportType) {}
