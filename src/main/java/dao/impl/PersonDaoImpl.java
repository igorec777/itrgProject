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
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Person existPerson = session.load(Person.class,id);
        try {
            Hibernate.initialize(existPerson.getRoles());
        } catch (ObjectNotFoundException ex) {
            session.getTransaction().rollback();
            System.out.println(Person.class.getSimpleName() + " with id:" + id + " not found");
            throw new RowNotFoundException(Person.class, id);
        }
        finally {
            sessionUtil.closeSessionIfOpen();
        }
        return existPerson;
    }

    @Override
    public Set<Person> findAll() {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Set<Person> people = session.createQuery("select distinct p from Person p inner join fetch p.roles r",
                        Person.class)
                .getResultStream()
                .collect(Collectors.toSet());
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
        return people;
    }

    @Override
    public Person create(Person person) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Serializable createdId = session.save(person);
        Person createdPerson = session.load(Person.class, createdId);
        Hibernate.initialize(createdPerson.getRoles());
        sessionUtil.closeSessionIfOpen();
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

    //TODO fix
    @Override
    public void update(Person person) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        session.update(person);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
    }
}
