package app.dao;

import app.entity.Record;
import app.exception.RowNotFoundException;

import java.util.Set;

public interface RecordDao {
    Record findById(Long id) throws RowNotFoundException;

    Set<Record> findAll();

    Record create(Record record);

    void delete(Record record);
}
