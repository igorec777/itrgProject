package connectorService;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateService {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure().buildSessionFactory();
        } catch (HibernateException ex) {
            throw new ExceptionInInitializerError("Failed to connect hibernate" + ex);
        }
    }

    public static SessionFactory getSessionFactory()
            throws HibernateException {
        return sessionFactory;
    }
}
