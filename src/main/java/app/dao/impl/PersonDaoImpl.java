package app.dao.impl;

import app.connectorService.SessionUtil;
import app.dao.PersonDao;
import app.entity.Person;
import app.exception.RowNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.swing.text.html.parser.Entity;
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
        sessionUtil.openSession()
                    .beginTransaction();
        Person existPerson = sessionUtil.getSession().get(Person.class, id);
        if (existPerson == null) {
            throw new RowNotFoundException(Person.class.getSimpleName() + " with id:" + id + " not found");
        }
        return existPerson;
    }

    @Override
    public Set<Person> findAll() {
        sessionUtil.openSession()
                    .beginTransaction();
        return sessionUtil.getSession().createQuery("select p from Person p inner join fetch p.roles r",
                        Person.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Person create(Person person) {
        sessionUtil.openSession()
                    .beginTransaction();
        Serializable createdId = sessionUtil.getSession().save(person);
        Person createdPerson = sessionUtil.getSession().load(Person.class, createdId);
        sessionUtil.commitAndClose();
        return createdPerson;
    }

    @Override
    public void delete(Person person) {
        sessionUtil.openSession()
                    .beginTransaction()
                    .getSession()
                    .remove(person);
        sessionUtil.commitAndClose();
    }

    @Override
    public void update(Person person) {
        sessionUtil.openSession()
                .beginTransaction()
                .getSession()
                .update(person);
        sessionUtil.commitAndClose();
    }
}
