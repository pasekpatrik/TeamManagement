package cz.cvut.fel.teammanagement.dto;

import java.time.LocalDate;

public record AccountDTO(Long id,
                         String firstName,
                         String lastName,
                         String email,
                         String phone,
                         LocalDate birthday) {}
