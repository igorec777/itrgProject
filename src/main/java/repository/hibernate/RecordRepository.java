package repository.hibernate;

import entity.hibernate.Record;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RecordRepository {

    Record create(Record record, Long clientId, Long workerId, Long serviceId);

    Set<Record> readAll();

    Record readById(Long id);

    void deleteById(Long id);
}
