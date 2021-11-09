package dao.impl;

import dao.ServiceDao;
import connectorService.SessionUtil;
import entity.Service;
import exceptions.RowNotFoundException;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ServiceDaoImpl implements ServiceDao {

    private final SessionUtil sessionUtil;

    @Autowired
    ServiceDaoImpl(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Service findById(Long id) throws RowNotFoundException {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Service existService = session.load(Service.class, id);
        try {
            Hibernate.initialize(existService);
            session.getTransaction().commit();
        } catch (ObjectNotFoundException ex) {
            session.getTransaction().rollback();
            System.out.println(Service.class.getSimpleName() + " with id:" + id + " not found");
            throw new RowNotFoundException(Service.class, id);
        }
        finally {
            sessionUtil.closeSessionIfOpen();
        }
        return existService;
    }

    @Override
    public Set<Service> findAll() {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Set<Service> services = session.createQuery("select r from Service r", Service.class)
                .getResultStream()
                .collect(Collectors.toSet());
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
        return services;
    }

    @Override
    public Service create(Service service) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Serializable createdId = session.save(service);
        Service createdService = session.load(Service.class, createdId);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
        return createdService;
    }

    @Override
    public void delete(Service service) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        session.delete(service);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
    }
}
