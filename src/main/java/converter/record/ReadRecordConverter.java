package converter.record;

import dao.RecordDao;
import dto.record.ReadRecordDto;
import dto.role.RoleDto;
import entity.Record;
import entity.Role;

public interface ReadRecordConverter {

    default ReadRecordDto toDto(Record entity) {
        return null;
    }

    default Record fromDto(ReadRecordDto dto) {
        return null;
    }
}
