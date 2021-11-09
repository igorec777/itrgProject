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
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Serializable createdId = session.save(role);
        Role createdRole = session.load(Role.class, createdId);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
        return createdRole;
    }

    @Override
    public Role findById(Long id) throws RowNotFoundException {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Role existRole = session.load(Role.class, id);
        try {
            Hibernate.initialize(existRole);
            session.getTransaction().commit();
        } catch (ObjectNotFoundException ex) {
            session.getTransaction().rollback();
            System.out.println(Role.class.getSimpleName() + " with id:" + id + " not found");
            throw new RowNotFoundException(Role.class, id);
        }
        finally {
            sessionUtil.closeSessionIfOpen();
        }
        return existRole;
    }

    @Override
    public Set<Role> findAll() {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Set<Role> roles = session.createQuery("select r from Role r", Role.class)
                .getResultStream()
                .collect(Collectors.toSet());
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
        return roles;
    }

    //TODO person_has_role also deleted, fix that
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
