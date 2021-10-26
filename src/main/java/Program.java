import connectorService.FlywayService;
import connectorService.HibernateService;
import org.hibernate.SessionFactory;


public class Program {
    public static void main(String[] args) {

        FlywayService flywayService = new FlywayService();
        flywayService.migrate();
        SessionFactory sessionFactory = HibernateService.getSessionFactory();

        flywayService.clean();
        sessionFactory.close();

    }
}
