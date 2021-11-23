package app.dto.record;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CreateUpdateRecordDto {
    private final Long id;
    private final Timestamp startTime;
    private final Timestamp endTime;
    private final Long clientId;
    private final Long workerId;
    private final Long serviceId;
}
