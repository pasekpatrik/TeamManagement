package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.enums.SportType;
import cz.cvut.fel.teammanagement.model.Team;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamDAO extends AbstractDAO<Team> {
    public TeamDAO() {
        super(Team.class);
    }

    public List<Team> findTeamsByType(SportType type) {
        return em.createQuery("SELECT t FROM Team t WHERE t.sportType = :type", Team.class)
                .setParameter("type", type)
                .getResultList();
    }
}
