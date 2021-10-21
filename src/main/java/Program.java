import org.h2.jdbcx.JdbcConnectionPool;
import repositoryImpl.UserRepositoryImpl;
import repository.UserRepository;
import service.FlywayService;

import static properties.DataSourceParams.*;


public class Program {

    public static void main(String[] args) {
        FlywayService flywayService = new FlywayService();
        flywayService.migrate();

        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(URL, USER, PASSWORD);
        UserRepository userRepository = new UserRepositoryImpl(jdbcConnectionPool);
        jdbcConnectionPool.dispose();
    }
}
