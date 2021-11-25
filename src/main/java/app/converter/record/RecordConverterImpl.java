package app.converter.record;

import app.dto.record.CreateUpdateRecordDto;
import app.dto.record.ReadRecordDto;
import app.entity.Record;
import org.springframework.stereotype.Component;

@Component
public class RecordConverterImpl implements RecordConverter {

    @Override
    public ReadRecordDto toReadRecordDto(Record entity) {
        return ReadRecordDto.builder()
                .id(entity.getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .client(entity.getClient())
                .worker(entity.getWorker())
                .service(entity.getService())
                .build();
    }

    @Override
    public Record fromReadRecordDto(CreateUpdateRecordDto dto) {
        return Record.builder()
                .id(dto.getId())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }
}
