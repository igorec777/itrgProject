//package service;
//
//import org.hibernate.HibernateException;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateService {
//
//    private static SessionFactory sessionFactory;
//
//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                sessionFactory = new Configuration().configure().buildSessionFactory();
//            } catch (HibernateException ex) {
//                System.err.println("Failed to create SessionFactory" + ex);
//            }
//        }
//        return sessionFactory;
//    }
//}
