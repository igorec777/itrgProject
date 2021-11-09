package service.impl;

import converter.record.CreateUpdateRecordConverterImpl;
import converter.record.ReadRecordConverterImpl;
import dao.PersonDao;
import dao.RecordDao;
import dao.ServiceDao;
import dto.record.CreateUpdateRecordDto;
import dto.record.ReadRecordDto;
import entity.Record;
import exceptions.RowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import service.RecordService;

import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class RecordServiceImpl implements RecordService {

    private final RecordDao recordDao;
    private final PersonDao personDao;
    private final ServiceDao serviceDao;
    private final ReadRecordConverterImpl readConverter;
    private final CreateUpdateRecordConverterImpl createUpdateConverter;


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
    public ReadRecordDto create(CreateUpdateRecordDto recordDto) throws RowNotFoundException {
        //also check if client, worker and service exist
        Record record = createUpdateConverter.fromDto(recordDto);
        record.setClient(personDao.findById(recordDto.getClientId()));
        record.setWorker(personDao.findById(recordDto.getWorkerId()));
        record.setService(serviceDao.findById(recordDto.getServiceId()));
        return readConverter.toDto(recordDao.create(record));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException {
        Record existRecord = recordDao.findById(id);
        recordDao.delete(existRecord);
    }
}
