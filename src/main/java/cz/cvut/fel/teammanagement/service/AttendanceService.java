package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.repository.AttendanceDAO;
import cz.cvut.fel.teammanagement.repository.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService extends AbstractService<Attendance> {
    private final AttendanceDAO attendanceDAO;
    private final EventDAO eventDAO;
    private final EventService eventService;

    @Autowired
    public AttendanceService(AttendanceDAO attendanceDAO, EventDAO eventDAO, EventService eventService) {
        super(attendanceDAO);
        this.attendanceDAO = attendanceDAO;
        this.eventDAO = eventDAO;
        this.eventService = eventService;
    }

    public boolean registerAttendance(Account account, Event event, StatusType statusType) {
        if (event.getStartDate().isBefore(LocalDate.now())) {
            return false;
        }

        List<Attendance> attendances = eventService.findAttendancesByEventId(event.getId());
        boolean alreadyRegistered = attendances.stream()
            .anyMatch(a -> a.getAccount().getId().equals(account.getId()));
        if (alreadyRegistered) {
            return false;
        }
        Attendance attendance = new Attendance();
        attendance.setAccount(account);
        attendance.setEvent(event);
        attendance.setStatusType(statusType);
        attendanceDAO.persist(attendance);
        attendances = eventService.findAttendancesByEventId(event.getId());
        account.setAttendances(attendances);
        event.setAttendances(attendances);
        return true;
    }

    public boolean changeAttendanceStatus(Attendance attendance, StatusType newStatus) {
        if (attendance != null && newStatus != null) {
            attendance.setStatusType(newStatus);
            attendanceDAO.update(attendance);
            return true;
        }
        return false;
    }
}
