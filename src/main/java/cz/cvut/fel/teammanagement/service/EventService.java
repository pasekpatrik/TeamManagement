package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.exceptions.IncorrectTeamException;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import cz.cvut.fel.teammanagement.repository.EventDAO;
import cz.cvut.fel.teammanagement.repository.AttendanceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService extends AbstractService<Event> {
    private final EventDAO eventDAO;
    private final AttendanceDAO attendanceDAO;
    private final AccountService accountService;
    private final AccountDAO accountDAO;

    @Autowired
    public EventService(EventDAO eventDAO, AttendanceDAO attendanceDAO, AccountService accountService, AccountDAO accountDAO) {
        super(eventDAO);
        this.eventDAO = eventDAO;
        this.attendanceDAO = attendanceDAO;
        this.accountService = accountService;
        this.accountDAO = accountDAO;
    }

    @Transactional
    public boolean addAttendanceToEvent(Long eventId, Long accountId, StatusType statusType) {


        Event event = getById(eventId);
        Account account = accountService.getById(accountId);

        if ( event == null || account == null || statusType == null) {
            return false;
        }

        boolean alreadyExists = event.getAttendances().stream()
            .anyMatch(a -> a.getAccount() != null && a.getAccount().getId().equals(account.getId()));

        if (alreadyExists) {
            return false;
        }

        if(!event.getTeam().getAccounts().contains(account)) {
            throw new IncorrectTeamException("Account does not belong to the event's team.");
        }

        Attendance attendance = new Attendance();
        attendance.setEvent(event);
        attendance.setAccount(account);
        attendance.setStatusType(statusType);
        attendanceDAO.persist(attendance);

        event.addAttendance(attendance);
        eventDAO.persist(event);

        account.addAttandance(attendance);
        accountDAO.persist(account);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Account> getEventAttendances(Long eventId) {
        Event event = eventDAO.find(eventId);
        return event != null && event.getAttendances() != null
            ? event.getAttendances().stream().map(Attendance::getAccount).toList()
            : List.of();
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsForAccount(Account account) {
        return account != null ? account.getAttendances().stream().map(Attendance::getEvent).toList() : List.of();
    }

    @Transactional
    public boolean removeAttendanceFromEvent(Long attendanceId) {
        Attendance attendance = attendanceDAO.find(attendanceId);
        if (attendance != null) {
            Event event = attendance.getEvent();
            if (event != null) {
                event.getAttendances().remove(attendance);
            }
            Account account = attendance.getAccount();
            if (account != null && account.getAttendances() != null) {
                account.getAttendances().remove(attendance);
            }
            attendance.setEvent(null);
            attendance.setAccount(null);
            attendanceDAO.delete(attendance);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Attendance> findAttendancesByEventId(Long eventId) {
        Event event = eventDAO.find(eventId);
        if (event != null && event.getAttendances() != null) {
            return event.getAttendances();
        }
        return List.of();
    }
}
