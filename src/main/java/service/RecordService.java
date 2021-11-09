package service;

import dto.record.CreateUpdateRecordDto;
import dto.record.ReadRecordDto;
import exceptions.RowNotFoundException;

import java.util.Set;

public interface RecordService {

    ReadRecordDto findById(Long id) throws RowNotFoundException;

    Set<ReadRecordDto> findAll();

    ReadRecordDto create(CreateUpdateRecordDto recordDto) throws RowNotFoundException;

    void deleteById(Long id) throws RowNotFoundException;

}
