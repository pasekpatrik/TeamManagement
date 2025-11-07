package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.repository.EventDAO;
import cz.cvut.fel.teammanagement.repository.AttendanceDAO;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService extends AbstractService<Event> {
    private final EventDAO eventDAO;
    private final AttendanceDAO attendanceDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public EventService(EventDAO eventDAO, AttendanceDAO attendanceDAO, AccountDAO accountDAO) {
        super(eventDAO);
        this.eventDAO = eventDAO;
        this.attendanceDAO = attendanceDAO;
        this.accountDAO = accountDAO;
    }

    @Transactional
    public boolean addAttendanceToEvent(Event event, Account account, StatusType statusType) {
        if (event != null && account != null && statusType != null) {
            Attendance attendance = new Attendance();
            attendance.setEvent(event);
            attendance.setAccount(account);
            attendance.setStatusType(statusType);
            attendanceDAO.persist(attendance);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Account> getEventAttendances(Long eventId) {
        Event event = eventDAO.find(eventId);
        return event != null && event.getAttendances() != null
            ? event.getAttendances().stream().map(Attendance::getAccount).toList()
            : List.of();
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsForAccount(Long accountId) {
        Account account = accountDAO.find(accountId);
        return account != null ? account.getAttendances().stream().map(Attendance::getEvent).toList() : List.of();
    }

    @Transactional
    public boolean removeAttendanceFromEvent(Long attendanceId) {
        Attendance attendance = attendanceDAO.find(attendanceId);
        if (attendance != null) {
            attendanceDAO.delete(attendance);
            return true;
        }
        return false;
    }
}
