package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.enums.EventType;
import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import cz.cvut.fel.teammanagement.repository.AttendanceDAO;
import cz.cvut.fel.teammanagement.repository.EventDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static cz.cvut.fel.teammanagement.enums.EventType.TRAINING;
import static cz.cvut.fel.teammanagement.enums.StatusType.PRESENT;
import static java.time.LocalTime.NOON;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AttendanceServiceTest {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private EventService eventService;

    private Account account;
    private Event futureEvent;
    private Event pastEvent;
    private Event todayEvent;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setFirstName("Test");
        account.setLastName("User");
        account.setEmail("testuser@example.com");
        account.setBirthday(LocalDate.of(2000, 1, 1));
        accountDAO.persist(account);

        futureEvent = new Event();
        futureEvent.setName("Future Event");
        futureEvent.setStartDate(LocalDate.now().plusDays(1));
        futureEvent.setStartTime(NOON);
        futureEvent.setAddress("Address");
        futureEvent.setEventType(TRAINING);
        futureEvent.setCity("Test City");
        eventDAO.persist(futureEvent);

        pastEvent = new Event();
        pastEvent.setName("Past Event");
        pastEvent.setStartDate(LocalDate.now().minusDays(1));
        pastEvent.setStartTime(NOON);
        pastEvent.setAddress("Address");
        pastEvent.setEventType(TRAINING);
        pastEvent.setCity("Test City");
        eventDAO.persist(pastEvent);

        todayEvent = new Event();
        todayEvent.setName("Past Event");
        todayEvent.setStartDate(LocalDate.now());
        todayEvent.setStartTime(NOON);
        todayEvent.setAddress("Address");
        todayEvent.setEventType(TRAINING);
        todayEvent.setCity("Test City");
        eventDAO.persist(todayEvent);
    }

    @Test
    void testRegisterAttendanceForFutureEvent() {
        boolean result = attendanceService.registerAttendance(account, futureEvent, PRESENT);
        assertFalse(result);
        List<Attendance> attendances = eventService.findAttendancesByEventId(pastEvent.getId());
        assertEquals(0, attendances.size());
    }

    @Test
    void testRegisterAttendanceForPastEventFails() {
        boolean result = attendanceService.registerAttendance(account, pastEvent, PRESENT);
        assertTrue(result);
        List<Attendance> attendances = eventService.findAttendancesByEventId(pastEvent.getId());
        assertEquals(1, attendances.size());
        assertEquals(account.getId(), attendances.get(0).getAccount().getId());
    }

    @Test
    void testRegisterDuplicateAttendanceFails() {
        boolean first = attendanceService.registerAttendance(account, pastEvent, PRESENT);
        boolean second = attendanceService.registerAttendance(account, pastEvent, PRESENT);
        assertTrue(first);
        assertFalse(second);
        List<Attendance> attendances = eventService.findAttendancesByEventId(pastEvent.getId());
        assertEquals(1, attendances.size());
    }

    @Test
    void testRegisterAttendanceForDifferentAccounts() {
        Account another = new Account();
        another.setFirstName("Another");
        another.setLastName("User");
        another.setEmail("another@example.com");
        another.setBirthday(LocalDate.of(1995, 5, 5));
        accountDAO.persist(another);
        boolean first = attendanceService.registerAttendance(account, pastEvent, PRESENT);
        boolean second = attendanceService.registerAttendance(another, pastEvent, PRESENT);
        assertTrue(first);
        assertTrue(second);
        List<Attendance> attendances = eventService.findAttendancesByEventId(pastEvent.getId());
        assertEquals(2, attendances.size());
    }

    @Test
    void testRegisterAttendanceTodaysDate() {
        Account another = new Account();
        another.setFirstName("Another");
        another.setLastName("User");
        another.setEmail("another@example.com");
        another.setBirthday(LocalDate.of(1995, 5, 5));
        accountDAO.persist(another);
        boolean first = attendanceService.registerAttendance(account, todayEvent, PRESENT);
        assertTrue(first);
        List<Attendance> attendances = eventService.findAttendancesByEventId(todayEvent.getId());
        assertEquals(1, attendances.size());
    }
}

