package converter.record;

import dto.record.CreateUpdateRecordDto;
import entity.Record;

public interface CreateUpdateRecordConverter {

    default CreateUpdateRecordDto toDto(Record entity) {
        return null;
    }

    default Record fromDto(CreateUpdateRecordDto dto) {
        return null;
    }
}
