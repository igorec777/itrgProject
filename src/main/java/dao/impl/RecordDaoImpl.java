package dao.impl;

import connectorService.SessionUtil;
import dao.RecordDao;
import entity.Person;
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
        sessionUtil.openSession();
        sessionUtil.beginTransaction();

        Record existRecord = sessionUtil.getSession().get(Record.class, id);
        if (existRecord == null) {
            String exMessage = Record.class.getSimpleName() + " with id:" + id + " not found";
            System.out.println("Log: " + exMessage);
            throw new RowNotFoundException(exMessage);
        }
        return existRecord;
    }

    @Override
    public Set<Record> findAll() {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        return sessionUtil.getSession().createQuery("select r from Record r",
                        Record.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Record create(Record record) {
        sessionUtil.openSession();
        sessionUtil.beginTransaction();
        Serializable createdId = sessionUtil.getSession().save(record);
        Record createdRecord = sessionUtil.getSession().load(Record.class, createdId);
        sessionUtil.commitAndClose();
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
