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

    public Session getNewSession() {
        openSession();
        return session;
    }

    private void openSession() {
        closeSessionIfOpen();
        session = sessionFactory.openSession();
    }

    public void closeSessionIfOpen() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
