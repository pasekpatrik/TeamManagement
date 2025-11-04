package cz.cvut.fel.teammanagement;

import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.repository.AccountDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DatabaseIntegrationTest {

    @Autowired
    private AccountDAO accountDAO;

    @Test
    void testAccountCrudOperations() {
        // Create
        Account account = new Account("firstname",
                "lastname",
                "testuser@example.com",
                "phone",
                LocalDate.now(),null,null,null);

        accountDAO.persist(account);
        assertNotNull(account.getId());

        // Read
        Account found = accountDAO.find(account.getId());
        assertEquals("firstname", found.getFirstName());
        assertEquals("testuser@example.com", found.getEmail());

        // Update
        found.setEmail("updated@example.com");
        Account updated = accountDAO.update(found);
        assertEquals("updated@example.com", updated.getEmail());

        // Find all
        List<Account> accounts = accountDAO.findAll();
        assertTrue(accounts.size() > 0);

        // Delete
        accountDAO.remove(updated);
        assertNull(accountDAO.find(updated.getId()));
    }
}

