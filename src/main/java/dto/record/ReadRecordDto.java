package dto.record;

import entity.Person;
import entity.Service;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ReadRecordDto {
    private final Long id;
    private final Timestamp startTime;
    private final Timestamp endTime;
    private final Person client;
    private final Person worker;
    private final Service service;
}
