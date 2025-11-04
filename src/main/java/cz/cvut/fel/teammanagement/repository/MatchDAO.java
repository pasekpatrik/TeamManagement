package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Match;
import org.springframework.stereotype.Repository;

@Repository
public class MatchDAO extends AbstractDAO<Match> {
    public MatchDAO() {
        super(Match.class);
    }
}

