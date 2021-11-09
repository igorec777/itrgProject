package converter.record;

import converter.BaseConverter;
import dto.record.ReadRecordDto;
import entity.Record;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ReadRecordConverterImpl implements BaseConverter<ReadRecordDto, Record> {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public ReadRecordDto toDto(Record entity) {
        return ReadRecordDto.builder()
                .id(entity.getId())
                .startTime(entity.getStartTime().toLocalDateTime().format(dateTimeFormatter))
                .endTime(entity.getEndTime().toLocalDateTime().format(dateTimeFormatter))
                .clientName(entity.getClient().getSurname() + " " + entity.getClient().getName())
                .workerName(entity.getWorker().getSurname() + " " + entity.getWorker().getName())
                .serviceInfo(entity.getService().getName() + ", " + entity.getService().getPrice() + "$")
                .build();
    }
}
