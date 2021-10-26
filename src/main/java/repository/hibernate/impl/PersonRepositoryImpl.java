package repository.hibernate.impl;

import entity.hibernate.Person;
import entity.hibernate.Role;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.hibernate.PersonRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonRepositoryImpl implements PersonRepository {

    private final SessionFactory sessionFactory;

    public PersonRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Person create(Person person) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Person createdPerson = session.load(Person.class, session.save(person));
            session.getTransaction().commit();
            return createdPerson;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Person addRoleByNameToPersonById(Long id, String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Person existPerson = session.load(Person.class, id);
            Role existRole = session.createQuery("select r from Role r where name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
            existPerson.getRoles().add(existRole);
            Person updatedPerson = (Person) session.merge(existPerson);
            session.getTransaction().commit();
            return updatedPerson;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        } catch (NoResultException ex) {
            System.out.println("Role with name(" + name + ") doesn't exist");
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }


    @Override
    public Set<Person> readAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Set<Person> people = session.createQuery("from Person", Person.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
            session.getTransaction().commit();
            return people;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Person readById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Person existPerson = session.get(Person.class, id);
            session.getTransaction().commit();
            return existPerson;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public void updateById(Long id, Person person) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Person existPerson = session.load(Person.class, id);
            existPerson.setName(person.getName());
            existPerson.setSurname(person.getSurname());
            existPerson.setLogin(person.getLogin());
            existPerson.setPassword(person.getPassword());
            session.update(existPerson);
            session.getTransaction().commit();
        } catch (HibernateException | EntityNotFoundException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Person existPerson = session.load(Person.class, id);
            session.remove(existPerson);
            session.getTransaction().commit();
        } catch (HibernateException | EntityNotFoundException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
