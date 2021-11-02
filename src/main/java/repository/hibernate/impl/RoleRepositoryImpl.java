package repository.hibernate.impl;

import entity.hibernate.Role;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.hibernate.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleRepositoryImpl implements RoleRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<Role> readAll() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Set<Role> roles = session.createQuery("select r from Role r", Role.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
            session.getTransaction().commit();
            return roles;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Role readByName(String name) {
        Role resultRole;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            resultRole = session.createQuery("select r from Role r where r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
            session.getTransaction().commit();
            return resultRole;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Role create(Role role) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Role createdRole = session.load(Role.class, session.save(role));
            session.getTransaction().commit();
            return createdRole;
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
            Role existRole = session.load(Role.class, id);
            session.remove(existRole);
            session.getTransaction().commit();
        } catch (HibernateException | EntityNotFoundException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }

    }

    @Override
    public void updateById(Long id, Role role) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Role existRole = session.load(Role.class, id);
            existRole.setName(role.getName());
            session.update(existRole);
            session.getTransaction().commit();
        } catch (HibernateException | EntityNotFoundException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
