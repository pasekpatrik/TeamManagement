package cz.cvut.fel.teammanagement.repository;

import cz.cvut.fel.teammanagement.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO extends AbstractDAO<Role> {
    public RoleDAO() {
        super(Role.class);
    }
}

