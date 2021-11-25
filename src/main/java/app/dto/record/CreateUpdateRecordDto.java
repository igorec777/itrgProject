package app.dto.record;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Builder
public class CreateUpdateRecordDto {
    private final Long id;

    @NotNull
    private final Timestamp startTime;

    @NotNull
    private final Timestamp endTime;

    @NotNull
    private final Long clientId;

    @NotNull
    private final Long workerId;

    @NotNull
    private final Long serviceId;
}
