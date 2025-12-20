package cz.cvut.fel.teammanagement.dto;

import cz.cvut.fel.teammanagement.model.Account;

import java.time.LocalDate;

public record AccountDTO(Long id,
                         String firstName,
                         String lastName,
                         String email,
                         String phone,
                         LocalDate birthday) {
    public AccountDTO(Account account) {
        this(account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getPhone(),
                account.getBirthday());
    }
}
