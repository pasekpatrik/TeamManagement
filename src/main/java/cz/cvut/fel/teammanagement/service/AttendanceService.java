package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.model.Match;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import cz.cvut.fel.teammanagement.repository.AttendanceDAO;
import cz.cvut.fel.teammanagement.repository.EventDAO;
import cz.cvut.fel.teammanagement.repository.MatchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService extends AbstractService<Attendance> {
    private final AttendanceDAO attendanceDAO;
    private final EventDAO eventDAO;
    private final MatchDAO matchDAO;
    private final EventService eventService;
    private final AccountDAO accountDAO;

    @Autowired
    public AttendanceService(AttendanceDAO attendanceDAO, EventDAO eventDAO, MatchDAO matchDAO, EventService eventService, AccountDAO accountDAO) {
        super(attendanceDAO);
        this.attendanceDAO = attendanceDAO;
        this.eventDAO = eventDAO;
        this.matchDAO = matchDAO;
        this.eventService = eventService;
        this.accountDAO = accountDAO;
    }

    @Transactional
    public boolean registerAttendance(Account account, Event event, StatusType statusType) {
        if (event.getStartDate().isAfter(LocalDate.now())) {
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
        account.addAttandance(attendance);
        accountDAO.persist(account);
        event.addAttendance(attendance);
        eventDAO.persist(event);
        return true;
    }

    @Transactional
    public boolean registerAttendance(Account account, Match match, StatusType statusType) {
        if (match.getStartDate().isAfter(LocalDate.now())) {
            return false;
        }

        List<Attendance> attendances = eventService.findAttendancesByEventId(match.getId());
        boolean alreadyRegistered = attendances.stream()
                .anyMatch(a -> a.getAccount().getId().equals(account.getId()));
        if (alreadyRegistered) {
            return false;
        }
        Attendance attendance = new Attendance();
        attendance.setAccount(account);
        attendance.setMatch(match);
        attendance.setStatusType(statusType);
        attendanceDAO.persist(attendance);
        account.addAttandance(attendance);
        accountDAO.persist(account);
        match.addAttendance(attendance);
        matchDAO.persist(match);
        return true;
    }

    @Transactional
    public boolean changeAttendanceStatus(Attendance attendance, StatusType newStatus) {
        if (attendance != null && newStatus != null) {
            attendance.setStatusType(newStatus);
            attendanceDAO.update(attendance);
            return true;
        }
        return false;
    }
}
