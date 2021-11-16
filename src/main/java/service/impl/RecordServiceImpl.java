package service.impl;

import converter.record.CreateUpdateRecordConverter;
import converter.record.ReadRecordConverter;
import converter.record.impl.CreateUpdateRecordConverterImpl;
import converter.record.impl.ReadRecordConverterImpl;
import dao.PersonDao;
import dao.RecordDao;
import dao.ServiceDao;
import dto.record.CreateUpdateRecordDto;
import dto.record.ReadRecordDto;
import entity.Record;
import exceptions.RecordOccupiedTimeException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import service.RecordService;

import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class RecordServiceImpl implements RecordService {

    private final RecordDao recordDao;
    private final PersonDao personDao;
    private final ServiceDao serviceDao;
    private final ReadRecordConverter readConverter;
    private final CreateUpdateRecordConverter createUpdateConverter;


    @Autowired
    RecordServiceImpl(RecordDao recordDao, PersonDao personDao, ServiceDao serviceDao,
                      ReadRecordConverterImpl readRecordConverter, CreateUpdateRecordConverterImpl createUpdateConverter) {
        this.recordDao = recordDao;
        this.personDao = personDao;
        this.serviceDao = serviceDao;
        this.readConverter = readRecordConverter;
        this.createUpdateConverter = createUpdateConverter;
    }

    @Override
    public ReadRecordDto findById(Long id) throws RowNotFoundException {
        return readConverter.toDto(recordDao.findById(id));
    }

    @Override
    public Set<ReadRecordDto> findAll() {
        return recordDao.findAll().stream()
                .map(readConverter::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ReadRecordDto create(CreateUpdateRecordDto recordDto) throws RowNotFoundException,
            UnavailableObjectException, RecordOccupiedTimeException {

        if (recordDto == null) {
            throw new UnavailableObjectException("'recordDto' is unavailable");
        }
        Record newRecord = createUpdateConverter.fromDto(recordDto);
        //also check if client, worker and service exist
        newRecord.setClient(personDao.findById(recordDto.getClientId()));
        newRecord.setWorker(personDao.findById(recordDto.getWorkerId()));
        newRecord.setService(serviceDao.findById(recordDto.getServiceId()));

        //check if time available
        if (!isTimeFree(recordDto)) {
            throw new RecordOccupiedTimeException("Time: " + recordDto.getStartTime() + " - " + recordDto.getEndTime()
                    + " to worker id:" + recordDto.getWorkerId() + " is occupied");
        }
        return readConverter.toDto(recordDao.create(newRecord));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException {
        Record existRecord = recordDao.findById(id);
        recordDao.delete(existRecord);
    }

    private boolean isTimeFree(CreateUpdateRecordDto recordDto) {
        Set<Record> records = recordDao.findAll();
        return records.stream()
                .filter(r -> recordDto.getWorkerId().equals(r.getWorker().getId()))
                .noneMatch(r -> recordDto.getStartTime().before(r.getStartTime()) &&
                        recordDto.getEndTime().after(r.getStartTime()) ||
                        recordDto.getStartTime().after(r.getStartTime()) &&
                        recordDto.getEndTime().before(r.getEndTime()));
    }
}
