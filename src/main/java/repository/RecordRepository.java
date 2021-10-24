package repository;

import entity.Record;

import java.util.List;

public interface RecordRepository {
    Record create(Record record);

    List<Record> readAll();

    Record readById(int id);

    int updateById(int id, Record record);

    int deleteById(int id);

    void deleteByPersonId(int id);

    void deleteByServiceId(int id);
}
