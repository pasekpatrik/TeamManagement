package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.enums.EventType;
import cz.cvut.fel.teammanagement.enums.SportType;
import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.exceptions.IncorrectTeamException;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.model.Team;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import cz.cvut.fel.teammanagement.repository.AttendanceDAO;
import cz.cvut.fel.teammanagement.repository.EventDAO;
import cz.cvut.fel.teammanagement.repository.TeamDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EventServiceTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private AttendanceDAO attendanceDAO;
    @Autowired
    private TeamDAO teamDAO;

    private Team team;
    private Account account;
    private Event event;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setName("Test Team");
        team.setCity("Test City");
        team.setSportType(SportType.HOCKEY);
        teamDAO.persist(team);

        account = new Account();
        account.setFirstName("Test");
        account.setLastName("User");
        account.setEmail("testuser@example.com");
        account.setBirthday(LocalDate.of(2000, 1, 1));
        account.setTeams(List.of(team));
        account.setAttendances(List.of());
        accountDAO.persist(account);
        team.setAccounts(List.of(account));

        event = new Event();
        event.setName("Test Event");
        event.setTeam(team);
        event.setAddress("123 Test St");
        event.setEventType(EventType.TRAINING);
        event.setStartDate(LocalDate.now().plusDays(1));
        event.setStartTime(LocalTime.now());
        event.setAttendances(List.of());
        eventDAO.persist(event);
    }

    @Test
    void testAddAttendanceToEvent_success() {
        boolean result = eventService.addAttendanceToEvent(event, account, StatusType.PRESENT);
        assertTrue(result);
        List<Attendance> attendances = eventService.findAttendancesByEventId(event.getId());
        assertEquals(1, attendances.size());
        assertEquals(account.getId(), attendances.get(0).getAccount().getId());
    }

    @Test
    void testAddAttendanceToEvent_incorrectTeam() {
        Account outsider = new Account();
        outsider.setFirstName("Out");
        outsider.setLastName("Sider");
        outsider.setEmail("outsider@test.com");
        outsider.setBirthday(LocalDate.of(1990, 1, 1));
        accountDAO.persist(outsider);
        assertThrows(IncorrectTeamException.class, () -> eventService.addAttendanceToEvent(event, outsider, StatusType.PRESENT));
    }

    @Test
    void testGetEventAttendances() {
        eventService.addAttendanceToEvent(event, account, StatusType.PRESENT);
        List<Account> accounts = eventService.getEventAttendances(event.getId());
        assertEquals(1, accounts.size());
        assertEquals(account.getId(), accounts.get(0).getId());
    }

    @Test
    void testGetEventsForAccount() {
        eventService.addAttendanceToEvent(event, account, StatusType.PRESENT);
        List<Event> events = eventService.getEventsForAccount(account);
        assertEquals(1, events.size());
        assertEquals(event.getId(), events.get(0).getId());
    }

    @Test
    void testRemoveAttendanceFromEvent() {
        eventService.addAttendanceToEvent(event, account, StatusType.PRESENT);
        List<Attendance> attendances = eventService.findAttendancesByEventId(event.getId());
        assertEquals(1, attendances.size());
        boolean removed = eventService.removeAttendanceFromEvent(attendances.get(0).getId());
        assertTrue(removed);
        List<Attendance> after = eventService.findAttendancesByEventId(event.getId());
        assertTrue(after.isEmpty());
    }
}

