package dao.impl;

import dao.RoleDao;
import connectorService.SessionUtil;
import entity.Role;
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
public class RoleDaoImpl implements RoleDao {

    private final SessionUtil sessionUtil;

    @Autowired
    RoleDaoImpl(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Role create(Role role) {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        Serializable createdId = sessionUtil.getSession().save(role);
        Role createdRole = sessionUtil.getSession().load(Role.class, createdId);
        sessionUtil.commitAndClose();
        return createdRole;
    }

    @Override
    public Role findById(Long id) throws RowNotFoundException {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        Role existRole = sessionUtil.getSession().get(Role.class, id);
        if (existRole == null) {
            String exMessage = Role.class.getSimpleName() + " with id:" + id + " not found";
            System.out.println("Log: " + exMessage);
            throw new RowNotFoundException(exMessage);
        }
        return existRole;
    }

    @Override
    public Set<Role> findAll() {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        return sessionUtil.getSession().createQuery("select r from Role r", Role.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public void update(Role role) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        session.update(role);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
    }

    @Override
    public void delete(Role role) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        session.delete(role);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
    }
}
