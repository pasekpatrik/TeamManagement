package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.TeamManagementApplication;
import cz.cvut.fel.teammanagement.enums.EventType;
import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@DataJpaTest
@ComponentScan(basePackageClasses = TeamManagementApplication.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE))
@ActiveProfiles("test")
public class AttendanceDAOTest {
    @Autowired
    private AttendanceDAO attendanceDAO;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findAttendanceByEventIdReturnAttendance() {
        Event event = new Event();
        event.setName("Event");
        event.setAddress("Address");
        event.setCity("City");
        event.setEventType(EventType.TRAINING);
        event.setStartDate(LocalDate.now());
        event.setStartTime(LocalTime.now());
        entityManager.persist(event);

        Attendance attendance = new Attendance();
        attendance.setEvent(event);
        attendance.setStatusType(StatusType.PRESENT);
        entityManager.persist(attendance);

        List<Attendance> attendances = attendanceDAO.findByEventId(event.getId());

        Assertions.assertFalse(attendances.isEmpty());
        Assertions.assertEquals(1, attendances.size());
        Assertions.assertEquals(event.getId(), attendances.get(0).getEvent().getId());
    }

    @Test
    public void findAttendancesByEventIdReturnAttendances() {
        Event event = new Event();
        event.setName("Event");
        event.setAddress("Address");
        event.setCity("City");
        event.setEventType(EventType.TRAINING);
        event.setStartDate(LocalDate.now());
        event.setStartTime(LocalTime.now());
        entityManager.persist(event);

        int sizeOfAttendance = 5;
        for (int i = 0; i < sizeOfAttendance; i++) {
            Attendance attendance = new Attendance();
            attendance.setEvent(event);
            attendance.setStatusType(StatusType.PRESENT);
            entityManager.persist(attendance);
        }

        List<Attendance> attendances = attendanceDAO.findByEventId(event.getId());

        Assertions.assertFalse(attendances.isEmpty());
        Assertions.assertEquals(sizeOfAttendance, attendances.size());
        for (Attendance attendance : attendances) {
            Assertions.assertEquals(event.getId(), attendance.getEvent().getId());
        }
    }

    @Test
    public void findAttendancesByEventIdReturnEmptyList() {
        Event event = new Event();
        event.setName("Event");
        event.setAddress("Address");
        event.setCity("City");
        event.setEventType(EventType.TRAINING);
        event.setStartDate(LocalDate.now());
        event.setStartTime(LocalTime.now());
        entityManager.persist(event);

        List<Attendance> attendances = attendanceDAO.findByEventId(event.getId());

        Assertions.assertTrue(attendances.isEmpty());
    }

    @Test
    public void findAttendancesByEventIdFiltersCorrectly() {
        Event event1 = new Event();
        event1.setName("Event1");
        event1.setAddress("Address");
        event1.setCity("City");
        event1.setEventType(EventType.TRAINING);
        event1.setStartDate(LocalDate.now());
        event1.setStartTime(LocalTime.now());
        entityManager.persist(event1);

        Event event2 = new Event();
        event2.setName("Event2");
        event2.setAddress("Address");
        event2.setCity("City");
        event2.setEventType(EventType.WORKSHOP);
        event2.setStartDate(LocalDate.now());
        event2.setStartTime(LocalTime.now());
        entityManager.persist(event2);

        Attendance a1 = new Attendance();
        a1.setEvent(event1);
        a1.setStatusType(StatusType.PRESENT);
        entityManager.persist(a1);

        Attendance a2 = new Attendance();
        a2.setEvent(event2);
        a2.setStatusType(StatusType.PRESENT);
        entityManager.persist(a2);

        List<Attendance> attendances = attendanceDAO.findByEventId(event1.getId());

        Assertions.assertEquals(1, attendances.size());
        Assertions.assertEquals(event1.getId(), attendances.get(0).getEvent().getId());
    }
}
