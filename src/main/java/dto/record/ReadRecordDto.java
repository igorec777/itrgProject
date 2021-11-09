package dto.record;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadRecordDto {
    private final Long id;
    private final String startTime;
    private final String endTime;
    private final String clientName;
    private final String workerName;
    private final String serviceInfo;
}
