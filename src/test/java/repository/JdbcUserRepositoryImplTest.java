package repository;


import model.User;
import org.junit.jupiter.api.Test;
import repositoryImpl.UserRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcUserRepositoryImplTest extends BaseRepositoryTest{

    private final UserRepository userRepository;

    JdbcUserRepositoryImplTest() {
        super();
        userRepository = new UserRepositoryImpl(getConnectionPool());
    }

    @Test
    void readAll() {
        List<User> resultUsers = userRepository.readAll();

        assertEquals(testUsers.size(), resultUsers.size());

        for (int i = 0; i < testUsers.size(); i++) {
            assertEquals(testUsers.get(i), resultUsers.get(i));
        }
    }

    @Test
    void readById() {

        for (int i = 1; i < testUsers.size(); i++) {
            assertEquals(testUsers.get(i - 1), userRepository.readById(i));
        }
    }

    @Test
    void create() {
        System.out.println("create");
        User newUser = new User(4,"Name4", "Surname4", "Login4", "4444");
        assertEquals(newUser, userRepository.create(newUser));
    }

    @Test
    void createAll() {
        System.out.println("create all");
        List<User> newUsers = List.of(
                new User(4,"Name4", "Surname4", "Login4", "4444"),
                new User(5,"Name5", "Surname5", "Login5", "5555")
        );
        assertEquals(newUsers, userRepository.createAll(newUsers));
    }

    @Test
    void deleteAll() {
        int affectedRows = userRepository.deleteAll();
        assertEquals(3, affectedRows);
    }

    @Test
    void deleteById() {
        int affectedRows;
        affectedRows = userRepository.deleteById(2);
        assertEquals(1, affectedRows);
        affectedRows = userRepository.deleteById(-5);
        assertEquals(0, affectedRows);
    }
}