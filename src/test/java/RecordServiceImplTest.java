import app.dto.record.CreateUpdateRecordDto;
import app.dto.record.ReadRecordDto;
import app.exception.RecordOccupiedTimeException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.isAllClassFieldsNotNullOfGivenObject;

public class RecordServiceImplTest extends BaseServiceTest {

    private final RecordService recordService;
    private static final Long RECORD_COUNT = 2L;

    @Autowired
    RecordServiceImplTest(RecordService recordService) {
        this.recordService = recordService;
    }

    @Test
    void findAll_shouldReturnSetOfRecords() throws IllegalAccessException {
        Set<ReadRecordDto> records = recordService.findAll();

        assertNotNull(records);
        assertEquals(RECORD_COUNT, records.size());
        for (ReadRecordDto r : records) {
            isAllClassFieldsNotNullOfGivenObject(r);
        }
    }

    @Test
    void findById_validData_shouldReturnExistRecord() throws RowNotFoundException, IllegalAccessException {
        for (Long i = 1L; i <= RECORD_COUNT; i++) {
            ReadRecordDto existRecord = recordService.findById(i);
            assertNotNull(existRecord);
            assertTrue(isAllClassFieldsNotNullOfGivenObject(existRecord));
        }
    }

    @Test
    void findById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> recordService.findById(999L));
        assertThrows(RowNotFoundException.class, () -> recordService.findById(-53L));
        assertThrows(RowNotFoundException.class, () -> recordService.findById(0L));
    }

    @Test
    void create_validData_shouldReturnCreatedRecord() throws RowNotFoundException,
            IllegalAccessException, UnavailableObjectException, RecordOccupiedTimeException {

        CreateUpdateRecordDto newRecord = CreateUpdateRecordDto.builder()
                .startTime(Timestamp.valueOf("2021-10-14 17:30:00"))
                .endTime(Timestamp.valueOf("2021-10-14 18:15:00"))
                .clientId(2L)
                //worker 3 has no records (not busy)
                .workerId(3L)
                .serviceId(1L)
                .build();

        ReadRecordDto createdRecord = recordService.create(newRecord);

        assertNotNull(createdRecord);
        assertTrue(isAllClassFieldsNotNullOfGivenObject(createdRecord));
    }

    @Test
    void create_wrongData_shouldThrowException() {
        CreateUpdateRecordDto newRecord = CreateUpdateRecordDto.builder()
                .startTime(Timestamp.valueOf("2021-10-14 17:30:00"))
                .endTime(Timestamp.valueOf("2021-10-14 18:15:00"))
                .clientId(1L)
                .workerId(3L)
                //service doesn't exist
                .serviceId(9999L)
                .build();

        assertThrows(RowNotFoundException.class, () -> recordService.create(newRecord));
    }

    @Test
    void create_noSuitableTime_shouldThrowException() {
        CreateUpdateRecordDto newRecord1 = CreateUpdateRecordDto.builder()
                //worker id:2 is busy at this time
                .startTime(Timestamp.valueOf("2021-09-25 11:10:00"))
                .endTime(Timestamp.valueOf("2021-09-25 12:00:00"))
                .clientId(1L)
                .workerId(2L)
                .serviceId(3L)
                .build();

        assertThrows(RecordOccupiedTimeException.class, () -> recordService.create(newRecord1));

        CreateUpdateRecordDto newRecord2 = CreateUpdateRecordDto.builder()
                //worker id:2 is busy at this time
                .startTime(Timestamp.valueOf("2021-09-25 11:10:00"))
                .endTime(Timestamp.valueOf("2021-09-25 16:00:00"))
                .clientId(1L)
                .workerId(2L)
                .serviceId(3L)
                .build();

        assertThrows(RecordOccupiedTimeException.class, () -> recordService.create(newRecord2));

        CreateUpdateRecordDto newRecord3 = CreateUpdateRecordDto.builder()
                //worker id:2 is busy at this time
                .startTime(Timestamp.valueOf("2021-09-25 12:10:00"))
                .endTime(Timestamp.valueOf("2021-09-25 12:20:00"))
                .clientId(1L)
                .workerId(2L)
                .serviceId(3L)
                .build();

        assertThrows(RecordOccupiedTimeException.class, () -> recordService.create(newRecord3));

        CreateUpdateRecordDto newRecord4 = CreateUpdateRecordDto.builder()
                //worker id:2 is busy at this time
                .startTime(Timestamp.valueOf("2021-09-25 12:10:00"))
                .endTime(Timestamp.valueOf("2021-09-25 17:00:00"))
                .clientId(1L)
                .workerId(2L)
                .serviceId(3L)
                .build();

        assertThrows(RecordOccupiedTimeException.class, () -> recordService.create(newRecord4));
    }

    @Test
    void deleteById_validData_shouldPass() throws RowNotFoundException {
        for (long i = 1L; i <= RECORD_COUNT; i++) {
            ReadRecordDto recordDto = ReadRecordDto.builder()
                    .id(i)
                    .build();
            recordService.deleteById(recordDto.getId());
        }
    }

    @Test
    void deleteById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> recordService.deleteById(ReadRecordDto.builder()
                .id(0L)
                .build().getId()));
        assertThrows(RowNotFoundException.class, () -> recordService.deleteById(ReadRecordDto.builder()
                .id(-45L)
                .build().getId()));
        assertThrows(RowNotFoundException.class, () -> recordService.deleteById(ReadRecordDto.builder()
                .id(95844L)
                .build().getId()));
    }
}
