package app.dao.impl;

import app.connectorService.SessionUtil;
import app.dao.RecordDao;
import app.entity.Record;
import app.exception.RowNotFoundException;
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
        sessionUtil.openSession()
                    .beginTransaction();

        Record existRecord = sessionUtil.getSession().get(Record.class, id);
        if (existRecord == null) {
            throw new RowNotFoundException(Record.class.getSimpleName() + " with id:" + id + " not found");
        }
        return existRecord;
    }

    @Override
    public Set<Record> findAll() {
        sessionUtil.openSession()
                    .beginTransaction();
        return sessionUtil.getSession().createQuery("select r from Record r",
                        Record.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Record create(Record record) {
        sessionUtil.openSession()
                    .beginTransaction();
        Serializable createdId = sessionUtil.getSession().save(record);
        Record createdRecord = sessionUtil.getSession().load(Record.class, createdId);
        sessionUtil.commitAndClose();
        return createdRecord;
    }

    @Override
    public void delete(Record record) {
        sessionUtil.openSession()
                .beginTransaction()
                .getSession()
                .delete(record);
        sessionUtil.commitAndClose();
    }
}
