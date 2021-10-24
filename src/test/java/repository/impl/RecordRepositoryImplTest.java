package repository.impl;

import entity.Person;
import entity.Record;
import entity.Service;
import org.junit.jupiter.api.Test;
import repository.BaseRepositoryTest;
import repository.RecordRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RecordRepositoryImplTest extends BaseRepositoryTest {

    private final RecordRepository recordRepository;
    private static final List<Record> testRecords = List.of(
            new Record(
                    1,
                    Date.valueOf("2021-10-26"),
                    new Time(12,20,0),
                    new Time(14,20,0),
                    new Person(1,"Name1", "Surname1", "Login1", "1111"),
                    new Person(2,"Name2", "Surname2", "Login2", "2222"),
                    new Service(3,"Pedicure", 65.8)),
            new Record(
                    2,
                    Date.valueOf("2021-10-27"),
                    new Time(18,35,0),
                    new Time(19,0,0),
                    new Person(3,"Name3", "Surname3", "Login3", "3333"),
                    new Person(2,"Name2", "Surname2", "Login2", "2222"),
                    new Service(2,"Sugaring", 40.2))
            );


    RecordRepositoryImplTest() {
        recordRepository = new RecordRepositoryImpl(getConnectionPool());
    }

    @Test
    void readAll() {
        List<Record> resultRecords = recordRepository.readAll();

        assertEquals(testRecords.size(), resultRecords.size());

        for (int i = 0; i < testRecords.size(); i++) {
            assertEquals(testRecords.get(i), resultRecords.get(i));
        }
    }

    @Test
    void readById() {
        for (int i = 1; i < testRecords.size(); i++) {
            assertEquals(testRecords.get(i - 1), recordRepository.readById(i));
        }
    }

    @Test
    void updateById() {
        Record updatedRecord = new Record(
                Date.valueOf("2021-10-30"),
                new Time(15,0,0),
                new Time(16,20,0),
                new Person(1,"Name1", "Surname1", "Login1", "1111"),
                new Person(3,"Name3", "Surname3", "Login3", "3333"),
                new Service(3,"Pedicure", 80.54));
        assertEquals(1, recordRepository.updateById(2, updatedRecord));
    }

    @Test
    void create() {
        Record newRecord = new Record(
                3,
                Date.valueOf("2021-11-06"),
                new Time(10,30,0),
                new Time(11,30,0),
                new Person(1,"Name1", "Surname1", "Login1", "1111"),
                new Person(2,"Name2", "Surname2", "Login2", "2222"),
                new Service(1,"Peeling", 50.4));

        assertEquals(newRecord, recordRepository.create(newRecord));
    }

    @Test
    void deleteById() {
        int affectedRows;
        affectedRows = recordRepository.deleteById(1);
        assertEquals(1, affectedRows);
        affectedRows = recordRepository.deleteById(999);
        assertEquals(0, affectedRows);
    }
}