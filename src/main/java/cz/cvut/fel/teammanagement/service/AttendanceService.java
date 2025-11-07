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
public class AttendanceService {
    private final AttendanceDAO attendanceDAO;
    private final EventDAO eventDAO;

    @Autowired
    public AttendanceService(AttendanceDAO attendanceDAO, EventDAO eventDAO) {
        this.attendanceDAO = attendanceDAO;
        this.eventDAO = eventDAO;
    }

    public boolean registerAttendance(Account account, Event event, StatusType statusType) {
        if (event.getStartDate().isBefore(LocalDate.now())) {
            return false;
        }
        List<Attendance> attendances = attendanceDAO.findByEventId(event.getId());
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
        return true;
    }

    public Attendance createAttendance(Attendance attendance) {
        attendanceDAO.persist(attendance);
        return attendance;
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceDAO.find(id);
    }

    public List<Attendance> getAllAttendances() {
        return attendanceDAO.findAll();
    }

    public Attendance updateAttendance(Attendance attendance) {
        return attendanceDAO.update(attendance);
    }

    public boolean deleteAttendance(Long id) {
        Attendance attendance = attendanceDAO.find(id);
        if (attendance != null) {
            attendanceDAO.delete(attendance);
            return true;
        }
        return false;
    }
}
