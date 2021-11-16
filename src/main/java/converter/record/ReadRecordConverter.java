package converter.record;

import dto.record.ReadRecordDto;
import entity.Record;

public interface ReadRecordConverter {

    default ReadRecordDto toDto(Record entity) {
        return null;
    }

    default Record fromDto(ReadRecordDto dto) {
        return null;
    }
}
