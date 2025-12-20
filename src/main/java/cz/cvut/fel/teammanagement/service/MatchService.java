package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.model.Match;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.repository.MatchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatchService extends AbstractService<Match> {
    private final MatchDAO matchDAO;

    @Autowired
    public MatchService(MatchDAO matchDAO) {
        super(matchDAO);
        this.matchDAO = matchDAO;
    }

    @Transactional(readOnly = true)
    public List<Match> findFutureMatchesForTeam(Long teamId, LocalDate date) {
        return matchDAO.findAll().stream()
            .filter(m -> m.getTeam() != null && m.getTeam().getId().equals(teamId))
            .filter(m -> m.getStartDate() != null && m.getStartDate().isAfter(date))
            .toList();
    }

    @Transactional(readOnly = true)
    public List<Attendance> findAttendancesByMatchId(Long matchId) {
        Match match = matchDAO.find(matchId);
        if (match != null && match.getAttendances() != null) {
            return match.getAttendances();
        }
        return List.of();
    }
}
