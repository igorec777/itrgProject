package converter.record;

import converter.BaseConverter;
import dto.record.CreateUpdateRecordDto;
import entity.Record;
import org.springframework.stereotype.Component;

@Component
public class CreateUpdateRecordConverterImpl implements BaseConverter<CreateUpdateRecordDto, Record> {

    @Override
    public Record fromDto(CreateUpdateRecordDto dto) {
        return Record.builder()
                .id(dto.getId())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }
}
