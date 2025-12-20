package cz.cvut.fel.teammanagement.controller;

import cz.cvut.fel.teammanagement.dto.AccountDTO;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<Account> accounts = accountService.getAll();

        return ResponseEntity.ok(accounts.stream()
                .map(AccountDTO::new)
                .toList());
    }

    @GetMapping("/getAccount/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getById(accountId);
        if(account == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new AccountDTO(account));
    }

    @PostMapping("/createAccount")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        Account account = new Account(accountDTO);
        account = accountService.create(account);

        return ResponseEntity.ok(new AccountDTO(account));
    }

    @PutMapping("/updateAccount/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId,@RequestBody AccountDTO accountDTO) {
        Account account = accountService.getById(accountId);
        if(account == null) {
            return ResponseEntity.notFound().build();
        }

        account.setFirstName(accountDTO.firstName());
        account.setLastName(accountDTO.lastName());
        account.setEmail(accountDTO.email());
        account.setPhone(accountDTO.phone());
        account.setBirthday(accountDTO.birthday());

        Account updated = accountService.update(account);
        return ResponseEntity.ok(new AccountDTO(updated));
    }

    @DeleteMapping("/deleteAccount/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        if(accountService.delete(accountId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
