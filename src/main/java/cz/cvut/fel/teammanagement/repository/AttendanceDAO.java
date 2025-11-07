package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Attendance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttendanceDAO extends AbstractDAO<Attendance> {
    public AttendanceDAO() {
        super(Attendance.class);
    }

    public List<Attendance> findByEventId(Long eventId) {
        return em.createQuery("SELECT a FROM Attendance a WHERE a.event.id = :eventId", Attendance.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }
}
