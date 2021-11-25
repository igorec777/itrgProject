package app.converter.record;

import app.dto.record.CreateUpdateRecordDto;
import app.dto.record.ReadRecordDto;
import app.entity.Record;

public interface RecordConverter {

    ReadRecordDto toReadRecordDto(Record entity);

    Record fromReadRecordDto(CreateUpdateRecordDto dto);
}
