package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Team;
import org.springframework.stereotype.Repository;

@Repository
public class TeamDAO extends AbstractDAO<Team> {
    public TeamDAO() {
        super(Team.class);
    }
}

