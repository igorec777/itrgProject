package service;

import dto.record.CreateUpdateRecordDto;
import dto.record.ReadRecordDto;
import exceptions.RecordOccupiedTimeException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;

import java.util.Set;

public interface RecordService {

    ReadRecordDto findById(Long id) throws RowNotFoundException;

    Set<ReadRecordDto> findAll();

    ReadRecordDto create(CreateUpdateRecordDto recordDto) throws RowNotFoundException, UnavailableObjectException, RecordOccupiedTimeException;

    //todo
    void deleteById(Long id) throws RowNotFoundException;

}
