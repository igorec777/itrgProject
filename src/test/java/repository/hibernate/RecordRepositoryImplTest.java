package repository.hibernate;

import entity.hibernate.Record;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RecordRepositoryImplTest extends BaseRepositoryTest {

    @Test
    void create_validData_shouldReturnCreatedRecord() {
        Record newRecord = new Record(
                Date.valueOf(LocalDate.of(2021, 10, 26)),
                Timestamp.valueOf("2021-10-26 12:20:00"),
                Timestamp.valueOf("2021-10-26 14:20:00")
        );
        assertEquals(newRecord, recordRepository.create(newRecord, 1L, 2L, 3L));
    }

    @Test
    void readAll_shouldReturnSetOfRecords() {
        Set<Record> resultRecords = recordRepository.readAll();
    }

    @Test
    void readById_validData_shouldReturnExistRecord() {
        recordRepository.readById(1L);
        recordRepository.readById(2L);
    }

    @Test
    void readById_wrongData_shouldReturnNull() {
        assertNull(recordRepository.readById(-43L));
        assertNull(recordRepository.readById(0L));
        assertNull(recordRepository.readById(95553L));
    }

    @Test
    void deleteById_validData_shouldReturnNothing() {
        recordRepository.deleteById(1L);
        recordRepository.deleteById(2L);
    }

    @Test
    void deleteById_wrongData_shouldReturnNothing() {
        recordRepository.deleteById(-19L);
        recordRepository.deleteById(0L);
        recordRepository.deleteById(54483L);
    }
}