import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import dto.record.CreateUpdateRecordDto;
import dto.record.ReadRecordDto;
import exceptions.RestrictionViolationException;
import exceptions.RowNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import service.RecordService;

import java.sql.Timestamp;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        System.out.println(records);
    }

    @Test
    void findById_validData_shouldReturnExistRecord() throws RowNotFoundException, IllegalAccessException {
        for (Long i = 1L; i <= RECORD_COUNT; i++) {
            ReadRecordDto existRecord = recordService.findById(i);
            assertNotNull(existRecord);
            assertTrue(isAllClassFieldsNotNullOfGivenObject(existRecord));
            System.out.println(existRecord);
        }
    }

    @Test
    void findById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> recordService.findById(999L));
        assertThrows(RowNotFoundException.class, () -> recordService.findById(-53L));
        assertThrows(RowNotFoundException.class, () -> recordService.findById(0L));
    }

    @Test
    void create_validData_shouldReturnCreatedRecord() throws RowNotFoundException, IllegalAccessException {
        CreateUpdateRecordDto newRecord = CreateUpdateRecordDto.builder()
                .startTime(Timestamp.valueOf("2021-10-14 17:30:00"))
                .endTime(Timestamp.valueOf("2021-10-14 18:15:00"))
                .clientId(2L)
                .workerId(3L)
                .serviceId(1L)
                .build();

        ReadRecordDto createdRecord = recordService.create(newRecord);

        assertNotNull(createdRecord);
        assertTrue(isAllClassFieldsNotNullOfGivenObject(createdRecord));

        System.out.println(createdRecord);
    }

    @Test
    void deleteById_validData_shouldPass() throws RowNotFoundException {
        for (long i = 1L; i <= RECORD_COUNT; i++) {
            ReadRecordDto recordDto = ReadRecordDto.builder()
                    .id(i)
                    .build();
            recordService.deleteById(recordDto.getId());
            System.out.println("Deleted: " + recordDto);
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
