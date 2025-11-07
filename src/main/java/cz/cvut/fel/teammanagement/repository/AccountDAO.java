package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO extends AbstractDAO<Account> {
    public AccountDAO() {
        super(Account.class);
    }
}
