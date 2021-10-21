package repository;

import model.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import service.FlywayService;

import java.util.ArrayList;
import java.util.List;

import static properties.DataSourceParams.*;

public class BaseRepositoryTest {
    public FlywayService flywayService;
    public JdbcConnectionPool connectionPool;

    public List<User> testUsers = new ArrayList<>();

    public BaseRepositoryTest() {
        flywayService = new FlywayService();
        connectionPool = JdbcConnectionPool.create(URL, USER, PASSWORD);
    }

    @BeforeEach
    public void initDB() {
        flywayService.migrate();
        initTestUsers();
    }

    @AfterEach
    public void cleanDB() {
        flywayService.clean();
    }


    protected JdbcConnectionPool getConnectionPool() {
        return connectionPool;
    }

    private void initTestUsers() {
        testUsers.add(new User(1, "Name1", "Surname1", "Login1", "1111"));
        testUsers.add(new User(2, "Name2", "Surname2", "Login2", "2222"));
        testUsers.add(new User(3, "Name3", "Surname3", "Login3", "3333"));
    }
}
