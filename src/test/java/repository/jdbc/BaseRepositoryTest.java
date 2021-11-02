package repository.jdbc;

import beansConfig.Config;
import connectorService.FlywayService;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static properties.DataSourceParams.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class BaseRepositoryTest {

    @Autowired
    private FlywayService flywayService;

    public static JdbcConnectionPool connectionPool;

    @BeforeAll
    static void openConnection() {
        connectionPool = JdbcConnectionPool.create(URL, USER, PASSWORD);
    }

    @BeforeEach
    public void initDB() {
        flywayService.migrate();
    }

    @AfterEach
    public void cleanDB() {
        flywayService.clean();
    }

    @AfterAll
    static void closeConnection() {
        connectionPool.dispose();
    }

    public static JdbcConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
