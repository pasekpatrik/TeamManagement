package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.enums.SportType;
import cz.cvut.fel.teammanagement.model.Match;
import cz.cvut.fel.teammanagement.model.Team;
import cz.cvut.fel.teammanagement.repository.MatchDAO;
import cz.cvut.fel.teammanagement.repository.TeamDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MatchServiceTest {
    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchDAO matchDAO;
    @Autowired
    private TeamDAO teamDAO;

    private Team team;
    private Match match;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setName("Test Team");
        team.setCity("Test City");
        team.setSportType(SportType.HOCKEY);
        teamDAO.persist(team);

        match = new Match();
        match.setTeam(team);
        match.setStartDate(LocalDate.now().plusDays(5));
        match.setAddress("Test st 123");
        match.setCity("Test City");
        match.setOpponentName("Test Opponent");
        match.setOpponentPhone("123123123");
        match.setStartDate(LocalDate.now().plusDays(5));
        match.setStartTime(LocalTime.now().plusHours(1));
        matchDAO.persist(match);
    }

    @Test
    void testFindFutureMatchesForTeam_found() {
        List<Match> matches = matchService.findFutureMatchesForTeam(team.getId(), LocalDate.now());
        assertFalse(matches.isEmpty());
        assertEquals(match.getId(), matches.get(0).getId());
    }

    @Test
    void testFindFutureMatchesForTeam_none() {
        List<Match> matches = matchService.findFutureMatchesForTeam(team.getId(), LocalDate.now().plusDays(10));
        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }
}
