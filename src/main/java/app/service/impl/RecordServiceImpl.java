package app.service.impl;

import app.converter.record.RecordConverter;
import app.dao.PersonDao;
import app.dao.RecordDao;
import app.dao.ServiceDao;
import app.dto.record.CreateUpdateRecordDto;
import app.dto.record.ReadRecordDto;
import app.entity.Record;
import app.exception.RecordOccupiedTimeException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class RecordServiceImpl implements RecordService {

    private final RecordDao recordDao;
    private final PersonDao personDao;
    private final ServiceDao serviceDao;
    private final RecordConverter recordConverter;


    @Autowired
    RecordServiceImpl(RecordDao recordDao, PersonDao personDao, ServiceDao serviceDao,
                      RecordConverter recordConverter) {
        this.recordDao = recordDao;
        this.personDao = personDao;
        this.serviceDao = serviceDao;
        this.recordConverter = recordConverter;
    }

    @Override
    public ReadRecordDto findById(Long id) throws RowNotFoundException {
        return recordConverter.toReadRecordDto(recordDao.findById(id));
    }

    @Override
    public Set<ReadRecordDto> findAll() {
        return recordDao.findAll().stream()
                .map(recordConverter::toReadRecordDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ReadRecordDto create(CreateUpdateRecordDto recordDto) throws RowNotFoundException,
            UnavailableObjectException, RecordOccupiedTimeException {

        if (recordDto == null) {
            throw new UnavailableObjectException("'recordDto' is unavailable");
        }
        Record newRecord = recordConverter.fromReadRecordDto(recordDto);
        //also check if client, worker and app.service exist
        newRecord.setClient(personDao.findById(recordDto.getClientId()));
        newRecord.setWorker(personDao.findById(recordDto.getWorkerId()));
        newRecord.setService(serviceDao.findById(recordDto.getServiceId()));

        //check if time available
        if (!isTimeFree(recordDto)) {
            throw new RecordOccupiedTimeException("Time: " + recordDto.getStartTime() + " - " + recordDto.getEndTime()
                    + " to worker id:" + recordDto.getWorkerId() + " is occupied");
        }
        return recordConverter.toReadRecordDto(recordDao.create(newRecord));
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
                        recordDto.getStartTime().before(r.getEndTime()));
    }
}
