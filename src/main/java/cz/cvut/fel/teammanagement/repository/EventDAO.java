package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventDAO extends AbstractDAO<Event> {
    public EventDAO() {
        super(Event.class);
    }
}
