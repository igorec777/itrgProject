package dao.impl;

import dao.ServiceDao;
import connectorService.SessionUtil;
import entity.Service;
import exceptions.RowNotFoundException;
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
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        Service existService = sessionUtil.getSession().get(Service.class, id);
        if (existService == null) {
            String exMessage = Service.class.getSimpleName() + " with id:" + id + " not found";
            System.out.println("Log: " + exMessage);
            throw new RowNotFoundException(exMessage);
        }
        return existService;
    }

    @Override
    public Set<Service> findAll() {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        return sessionUtil.getSession().createQuery("select r from Service r", Service.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Service create(Service service) {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        Serializable createdId = sessionUtil.getSession().save(service);
        Service createdService = sessionUtil.getSession().load(Service.class, createdId);
        sessionUtil.commitAndClose();
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
