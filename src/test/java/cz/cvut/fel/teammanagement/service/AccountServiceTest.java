package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.exceptions.NoEntityFoundException;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.model.Team;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountDAO accountDAO;

    private Account account;
    private Team team;
    private Event event;
    private Attendance attendance;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setFirstName("Test");
        account.setLastName("User");
        account.setEmail("testuser@example.com");
        account.setBirthday(LocalDate.of(2000, 1, 1));
        account.setTeams(Collections.emptyList());
        account.setAttendances(Collections.emptyList());
        accountDAO.persist(account);
    }

    @Test
    void testFindAccountByEmail_found() {
        Account found = accountService.findAccountByEmail("testuser@example.com");
        assertNotNull(found);
        assertEquals(account.getEmail(), found.getEmail());
    }

    @Test
    void testFindAccountByEmail_notFound() {
        assertThrows(NoEntityFoundException.class, () -> accountService.findAccountByEmail("notfound@example.com"));
    }

    @Test
    void testGetAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        assertFalse(accounts.isEmpty());
        assertTrue(accounts.stream().anyMatch(a -> a.getEmail().equals("testuser@example.com")));
    }

    @Test
    void testGetTeamsForAccount_empty() {
        List<Team> teams = accountService.getTeamsForAccount(account.getId());
        assertNotNull(teams);
        assertTrue(teams.isEmpty());
    }

    @Test
    void testGetTeamsForAccount_nullAccount() {
        assertThrows(NoEntityFoundException.class, () -> accountService.getTeamsForAccount(-1L));
    }

    @Test
    void testGetAttendancesForAccount_empty() {
        List<Event> events = accountService.getAttendancesForAccount(account.getId());
        assertNotNull(events);
        assertTrue(events.isEmpty());
    }

    @Test
    void testGetAttendancesForAccount_nullAccount() {
        assertThrows(NoEntityFoundException.class, () -> accountService.getAttendancesForAccount(-1L));
    }
}

