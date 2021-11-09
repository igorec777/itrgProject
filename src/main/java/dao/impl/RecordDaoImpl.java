package dao.impl;

import connectorService.SessionUtil;
import dao.RecordDao;
import entity.Record;
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
public class RecordDaoImpl implements RecordDao {

    private final SessionUtil sessionUtil;

    @Autowired
    RecordDaoImpl(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Record findById(Long id) throws RowNotFoundException {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Record existRecord = session.load(Record.class, id);
        try {
            Hibernate.initialize(existRecord.getClient());
            Hibernate.initialize(existRecord.getWorker());
            Hibernate.initialize(existRecord.getService());
            session.getTransaction().commit();
        } catch (ObjectNotFoundException ex) {
            session.getTransaction().rollback();
            String exMessage = Record.class.getSimpleName() + " with id:" + id + " not found";
            System.out.println("Log: " + Record.class.getSimpleName() + " with id:" + id + " not found");
            throw new RowNotFoundException(exMessage);
        } finally {
            sessionUtil.closeSessionIfOpen();
        }
        return existRecord;
    }

    @Override
    public Set<Record> findAll() {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Set<Record> records = session.createQuery("select r from Record r", Record.class)
                .getResultStream()
                .collect(Collectors.toSet());
        records.forEach(r -> {
            Hibernate.initialize(r.getClient());
            Hibernate.initialize(r.getWorker());
            Hibernate.initialize(r.getService());
        });
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
        return records;
    }

    @Override
    public Record create(Record record) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        Serializable createdId = session.save(record);
        Record createdRecord = session.load(Record.class, createdId);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
        return createdRecord;
    }

    @Override
    public void delete(Record record) {
        Session session = sessionUtil.getNewSession();
        session.beginTransaction();
        session.delete(record);
        session.getTransaction().commit();
        sessionUtil.closeSessionIfOpen();
    }
}
