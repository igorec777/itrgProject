package app.converter.record;

import app.dto.record.CreateUpdateRecordDto;
import app.entity.Record;

public interface CreateUpdateRecordConverter {

    default CreateUpdateRecordDto toDto(Record entity) {
        return null;
    }

    default Record fromDto(CreateUpdateRecordDto dto) {
        return null;
    }
}
