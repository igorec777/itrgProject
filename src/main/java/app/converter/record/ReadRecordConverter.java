package app.converter.record;

import app.dto.record.ReadRecordDto;
import app.entity.Record;

public interface ReadRecordConverter {

    default ReadRecordDto toDto(Record entity) {
        return null;
    }

    default Record fromDto(ReadRecordDto dto) {
        return null;
    }
}
