package repository.hibernate.impl;

import entity.hibernate.Service;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.hibernate.ServiceRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceRepositoryImpl implements ServiceRepository {

    private final SessionFactory sessionFactory;

    public ServiceRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<Service> readAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Set<Service> services = session.createQuery("from Service", Service.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
            session.getTransaction().commit();
            return services;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Service readById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Service existService = session.get(Service.class, id);
            session.getTransaction().commit();
            return existService;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Service create(Service service) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Service createdService = session.load(Service.class, session.save(service));
            session.getTransaction().commit();
            return createdService;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Service existService = session.load(Service.class, id);
            session.remove(existService);
            session.getTransaction().commit();
        } catch (HibernateException | EntityNotFoundException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void updateById(Long id, Service service) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Service existService = session.load(Service.class, id);
            existService.setName(service.getName());
            existService.setPrice(service.getPrice());
            session.update(existService);
            session.getTransaction().commit();
        } catch (HibernateException | EntityNotFoundException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
