package dao.impl;

import dao.PersonDao;
import connectorService.SessionUtil;
import entity.Person;
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
public class PersonDaoImpl implements PersonDao {

    private final SessionUtil sessionUtil;

    @Autowired
    PersonDaoImpl(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Person findById(Long id) throws RowNotFoundException {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        Person existPerson = sessionUtil.getSession().get(Person.class, id);
        if (existPerson == null) {
            String exMessage = Person.class.getSimpleName() + " with id:" + id + " not found";
            System.out.println("Log: " + exMessage);
            throw new RowNotFoundException(exMessage);
        }
        return existPerson;
    }

    @Override
    public Set<Person> findAll() {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        return sessionUtil.getSession().createQuery("select p from Person p inner join fetch p.roles r",
                        Person.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Person create(Person person) {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        Serializable createdId = sessionUtil.getSession().save(person);
        Person createdPerson = sessionUtil.getSession().load(Person.class, createdId);
        sessionUtil.commitAndClose();
        return createdPerson;
    }

    @Override
    public void delete(Person person) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        session.remove(person);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
    }

    @Override
    public void update(Person person) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        session.update(person);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
    }
}
