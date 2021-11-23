package app.converter.record.impl;

import app.converter.record.ReadRecordConverter;
import app.dto.record.ReadRecordDto;
import app.entity.Record;
import org.springframework.stereotype.Component;

@Component
public class ReadRecordConverterImpl implements ReadRecordConverter {

    @Override
    public ReadRecordDto toDto(Record entity) {
        return ReadRecordDto.builder()
                .id(entity.getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .client(entity.getClient())
                .worker(entity.getWorker())
                .service(entity.getService())
                .build();
    }
}
