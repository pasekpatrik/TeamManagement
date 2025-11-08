package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.Generator;
import cz.cvut.fel.teammanagement.TeamManagementApplication;
import cz.cvut.fel.teammanagement.enums.SportType;
import cz.cvut.fel.teammanagement.model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static cz.cvut.fel.teammanagement.enums.SportType.FOOTBALL;
import static cz.cvut.fel.teammanagement.enums.SportType.VOLLEYBALL;

@DataJpaTest
@ComponentScan(basePackageClasses = TeamManagementApplication.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE))
@ActiveProfiles("test")
public class TeamDAOTest {
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findTeamById() {
        Team team = Generator.generateTeam();
        entityManager.persist(team);

        long id = team.getId();
        Team result = teamDAO.find(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
    }

    @Test
    public void findAllAndReturnTeams() {
        int numberOfTeams = 10;

        for (int i = 0; i < numberOfTeams; i++) {
            Team team = Generator.generateTeam();
            entityManager.persist(team);
        }

        List<Team> teams = teamDAO.findAll();
        Assertions.assertNotNull(teams);
        Assertions.assertEquals(numberOfTeams, teams.size());
    }

    @Test
    public void findTeamsByTypeReturnTeam() {
        SportType type = FOOTBALL;

        Team team = Generator.generateTeam();
        team.setSportType(type);
        entityManager.persist(team);

        List<Team> teams = teamDAO.findTeamsByType(FOOTBALL);

        for (Team oneTeam : teams) {
            Assertions.assertEquals(type, oneTeam.getSportType());
        }
    }

    @Test
    public void findTeamsByTypeReturnEmptyList() {
        Team team = Generator.generateTeam();
        team.setSportType(FOOTBALL);
        entityManager.persist(team);

        List<Team> teams = teamDAO.findTeamsByType(VOLLEYBALL);

        Assertions.assertTrue(teams.isEmpty());
    }

    @Test
    public void findTeamsByTypeReturnTeams() {
        SportType type = FOOTBALL;
        int numberOfTeams = 10;

        for (int i = 0; i < numberOfTeams; i++) {
            Team team = Generator.generateTeam();
            team.setSportType(type);
            entityManager.persist(team);
        }

        List<Team> teams = teamDAO.findTeamsByType(FOOTBALL);

        for (Team oneTeam : teams) {
            Assertions.assertEquals(type, oneTeam.getSportType());
        }
    }
}
