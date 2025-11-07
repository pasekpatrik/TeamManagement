package cz.cvut.fel.teammanagement.service;

import cz.cvut.fel.teammanagement.model.Role;
import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    private final RoleDAO roleDAO;

    @Autowired
    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Transactional
    public Role createRole(Role role) {
        roleDAO.persist(role);
        return role;
    }

    @Transactional(readOnly = true)
    public Role findRoleById(Long id) {
        return roleDAO.find(id);
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDAO.findAll();
    }

    @Transactional
    public Role updateRole(Role role) {
        return roleDAO.update(role);
    }

    @Transactional
    public boolean deleteRole(Long id) {
        Role role = roleDAO.find(id);
        if (role != null) {
            roleDAO.delete(role);
            return true;
        }
        return false;
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

