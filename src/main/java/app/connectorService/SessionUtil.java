package app.connectorService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class SessionUtil {

    private final SessionFactory sessionFactory;
    private Session session;


    @Autowired
    SessionUtil(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return session;
    }

    public SessionUtil openSession() {
        closeSessionIfOpen();
        session = sessionFactory.openSession();
        return this;
    }

    public void closeSessionIfOpen() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public SessionUtil beginTransaction() {
        session.beginTransaction();
        return this;
    }

    public void commitAndClose() {
        session.getTransaction().commit();
        closeSessionIfOpen();
    }
}
