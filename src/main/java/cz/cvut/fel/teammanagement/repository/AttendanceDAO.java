package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Attendance;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDAO extends AbstractDAO<Attendance> {
    public AttendanceDAO() {
        super(Attendance.class);
    }
}
