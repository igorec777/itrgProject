package repository.hibernate;

import connectorService.FlywayService;
import connectorService.HibernateService;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseRepositoryTest {

    private final FlywayService flywayService = new FlywayService();
    private static final SessionFactory sessionFactory = HibernateService.getSessionFactory();

    @BeforeEach
    public void initDB() {
        flywayService.migrate();
    }

    @AfterEach
    public void cleanDB() {
        flywayService.clean();
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @AfterClass
    public static void closeSessionFactory() {
        sessionFactory.close();
    }

}
