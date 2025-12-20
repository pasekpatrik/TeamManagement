package cz.cvut.fel.teammanagement.dto;

import cz.cvut.fel.teammanagement.enums.StatusType;
import cz.cvut.fel.teammanagement.model.Attendance;

public record AttendanceDTO(StatusType statusType, long accountId, long matchId, long eventId) {
    public AttendanceDTO(Attendance attendance) {
        this(attendance.getStatusType(),
                attendance.getAccount() != null ? attendance.getAccount().getId() : 0L,
                attendance.getMatch() != null ? attendance.getMatch().getId() : 0L,
                attendance.getEvent() != null ? attendance.getEvent().getId() : 0L);
    }
}
