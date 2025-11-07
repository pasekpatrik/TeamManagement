package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.enums.StatusType;
import org.springframework.stereotype.Repository;

@Repository
public class EventDAO extends AbstractDAO<Event> {
    public EventDAO() {
        super(Event.class);
    }
}
