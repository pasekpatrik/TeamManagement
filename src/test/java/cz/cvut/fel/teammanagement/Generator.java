package cz.cvut.fel.teammanagement;

import cz.cvut.fel.teammanagement.model.Account;
import cz.cvut.fel.teammanagement.model.Team;
import java.time.LocalDate;
import java.util.Random;

import static cz.cvut.fel.teammanagement.enums.SportType.FOOTBALL;

public class Generator {

    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static int randomInt(int max) {
        return RAND.nextInt(max);
    }

    public static long randomLong() {
        return RAND.nextLong();
    }

    public static int randomInt(int min, int max) {
        assert min >= 0;
        assert min < max;

        int result;

        do {
            result = randomInt(max);
        } while (result < min);

        return result;
    }

    public static Account generateAccount() {
        Account account = new Account();

        account.setFirstName("FirstName " + randomInt());
        account.setLastName("LastName" + randomInt());
        account.setEmail("test" + randomInt() + "@email.com");
        account.setBirthday(LocalDate.now());

        return account;
    }

    public static Team generateTeam() {
        Team team = new Team();

        team.setName("Name" + randomInt());
        team.setCity("City" + randomInt());
        team.setSportType(FOOTBALL);

        return team;
    }
}
