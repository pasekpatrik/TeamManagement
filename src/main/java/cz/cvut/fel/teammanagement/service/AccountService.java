package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.exceptions.NoEntityFoundException;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Team;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    private final AccountDAO accountDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Transactional
    public Account createAccount(Account account) {
        accountDAO.persist(account);
        return account;
    }

    @Transactional
    public Account updateAccount(Account account) {
        return accountDAO.update(account);
    }

    @Transactional
    public boolean deleteAccount(Long accountId) {
        Account account = accountDAO.find(accountId);
        if (account != null) {
            accountDAO.delete(account);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Account findAccountById(Long accountId) {
        return accountDAO.find(accountId);
    }

    @Transactional(readOnly = true)
    public Account findAccountByEmail(String email) {
        return accountDAO.findAll().stream()
            .filter(acc -> acc.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .orElseThrow(() -> new NoEntityFoundException("No account with email " + email));
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountDAO.findAll();
    }

    @Transactional(readOnly = true)
    public List<Team> getTeamsForAccount(Long accountId) {
        Account account = accountDAO.find(accountId);
        return account != null ? account.getTeams() : List.of();
    }

    @Transactional(readOnly = true)
    public List<Event> getAttendancesForAccount(Long accountId) {
        Account account = accountDAO.find(accountId);
        return account != null && account.getAttendances() != null
            ? account.getAttendances().stream().map(Attendance::getEvent).toList()
            : List.of();
    }
}
