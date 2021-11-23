package app.converter.record.impl;

import app.converter.record.CreateUpdateRecordConverter;
import app.dto.record.CreateUpdateRecordDto;
import app.entity.Record;
import org.springframework.stereotype.Component;

@Component
public class CreateUpdateRecordConverterImpl implements CreateUpdateRecordConverter {

    @Override
    public Record fromDto(CreateUpdateRecordDto dto) {
        return Record.builder()
                .id(dto.getId())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }
}
