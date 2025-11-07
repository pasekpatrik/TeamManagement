package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Role;
import cz.cvut.fel.teammanagement.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService extends AbstractService<Role> {
    private final RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleDAO roleDAO) {
        super(roleDAO);
        this.roleDAO = roleDAO;
    }

    @Transactional(readOnly = true)
    public List<Role> getRolesForAccount(Long accountId) {
        return roleDAO.findAll().stream()
            .filter(r -> r.getAccount() != null && r.getAccount().getId().equals(accountId))
            .toList();
    }

    @Transactional(readOnly = true)
    public long countRolesByType(String type) {
        return roleDAO.findAll().stream()
            .filter(r -> r.getRoleType() != null && r.getRoleType().name().equalsIgnoreCase(type))
            .count();
    }
}
