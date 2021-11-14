package connectorService;

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

    //TODO deprecated
    public Session getNewSession() {
        openSession();
        return session;
    }

    public Session getSession() {
        return session;
    }

    public void openSession() {
        closeSessionIfOpen();
        session = sessionFactory.openSession();
    }

    public void closeSessionIfOpen() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public void beginTransaction() {
        session.beginTransaction();
    }

    public void commitAndClose() {
        session.getTransaction().commit();
        closeSessionIfOpen();
    }
    public void rollbackAndClose() {
        session.getTransaction().rollback();
        closeSessionIfOpen();
    }
}
