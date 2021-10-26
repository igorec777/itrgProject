package repository.hibernate.impl;

import entity.hibernate.Person;
import entity.hibernate.Record;
import entity.hibernate.Service;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.hibernate.RecordRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class RecordRepositoryImpl implements RecordRepository {

    private final SessionFactory sessionFactory;

    public RecordRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Record create(Record record, Long clientId, Long workerId, Long serviceId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Person existClient = session.load(Person.class, clientId);
            Person existWorker = session.load(Person.class, workerId);
            Service existService = session.load(Service.class, serviceId);

            record.setClient(existClient);
            record.setWorker(existWorker);
            record.setService(existService);
            Record createdRecord = session.load(Record.class, session.save(record));
            session.getTransaction().commit();
            return createdRecord;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Set<Record> readAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Set<Record> resultRecords = session.createQuery("from Record", Record.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
            session.getTransaction().commit();
            return resultRecords;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Record readById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Record existRecord = session.get(Record.class, id);
            session.getTransaction().commit();
            return existRecord;
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
            Record existRecord = session.get(Record.class, id);
            if (existRecord == null) {
                throw new HibernateException("Row with identifier " + id + " doesn't exist");
            }
            session.remove(existRecord);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
