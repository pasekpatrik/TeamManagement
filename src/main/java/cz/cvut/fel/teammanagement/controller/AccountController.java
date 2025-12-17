package cz.cvut.fel.teammanagement.controller;

import cz.cvut.fel.teammanagement.dto.AccountDTO;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountService.getAll();

        return accounts.stream()
                .map(account -> new AccountDTO(account.getId(),
                        account.getFirstName(),
                        account.getLastName(),
                        account.getEmail(),
                        account.getPhone(),
                        account.getBirthday()))
                .toList();
    }

    @GetMapping("/{accountId}")
    public AccountDTO getAccount(@PathVariable Long accountId) {
        Account account = accountService.getById(accountId);

        return new AccountDTO(account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getPhone(),
                account.getBirthday());
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        Account account = new Account();
        account.setFirstName(accountDTO.firstName());
        account.setLastName(accountDTO.lastName());
        account.setEmail(accountDTO.email());
        account.setPhone(accountDTO.phone());
        account.setBirthday(accountDTO.birthday());

        Account saved = accountService.create(account);

        AccountDTO response = new AccountDTO(saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getBirthday());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId,@RequestBody AccountDTO accountDTO) {
        Account account = accountService.getById(accountId);
        account.setFirstName(accountDTO.firstName());
        account.setLastName(accountDTO.lastName());
        account.setEmail(accountDTO.email());
        account.setPhone(accountDTO.phone());
        account.setBirthday(accountDTO.birthday());

        Account updated = accountService.update(account);
        AccountDTO response = new AccountDTO(updated.getId(),
                updated.getFirstName(),
                updated.getLastName(),
                updated.getEmail(),
                updated.getPhone(),
                updated.getBirthday());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.delete(accountId);
        return ResponseEntity.noContent().build();
    }
}
