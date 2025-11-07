package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Team;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Event;
import cz.cvut.fel.teammanagement.repository.TeamDAO;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TeamService {
    private final TeamDAO teamDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public TeamService(TeamDAO teamDAO, AccountDAO accountDAO) {
        this.teamDAO = teamDAO;
        this.accountDAO = accountDAO;
    }

    @Transactional
    public boolean addMemberToTeam(Long teamId, Long accountId) {
        Team team = teamDAO.find(teamId);
        Account account = accountDAO.find(accountId);
        if (team != null && account != null && !team.getAccounts().contains(account)) {
            team.getAccounts().add(account);
            teamDAO.update(team);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Event> getFutureEventsForTeam(Long teamId) {
        Team team = teamDAO.find(teamId);
        if (team != null && team.getEvents() != null) {
            LocalDate today = LocalDate.now();
            return team.getEvents().stream()
                .filter(event -> event.getStartDate() != null && event.getStartDate().isAfter(today))
                .toList();
        }
        return List.of();
    }
}
