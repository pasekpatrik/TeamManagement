package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Match;
import cz.cvut.fel.teammanagement.model.Attendance;
import cz.cvut.fel.teammanagement.repository.MatchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatchService {
    private final MatchDAO matchDAO;

    @Autowired
    public MatchService(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    @Transactional
    public Match createMatch(Match match) {
        matchDAO.persist(match);
        return match;
    }

    @Transactional(readOnly = true)
    public Match findMatchById(Long id) {
        return matchDAO.find(id);
    }

    @Transactional(readOnly = true)
    public List<Match> getAllMatches() {
        return matchDAO.findAll();
    }

    @Transactional
    public Match updateMatch(Match match) {
        return matchDAO.update(match);
    }

    @Transactional
    public boolean deleteMatch(Long id) {
        Match match = matchDAO.find(id);
        if (match != null) {
            matchDAO.delete(match);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Match> findFutureMatchesForTeam(Long teamId, LocalDate date) {
        return matchDAO.findAll().stream()
            .filter(m -> m.getTeam() != null && m.getTeam().getId().equals(teamId))
            .filter(m -> m.getStartDate() != null && m.getStartDate().isAfter(date))
            .toList();
    }

    @Transactional(readOnly = true)
    public List<Attendance> getAttendancesForMatch(Long matchId) {
        Match match = matchDAO.find(matchId);
        return match != null && match.getAttendances() != null ? match.getAttendances() : List.of();
    }
}
