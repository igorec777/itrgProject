package app.service;

import app.dto.record.CreateUpdateRecordDto;
import app.dto.record.ReadRecordDto;
import app.exception.RecordOccupiedTimeException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;

import java.util.Set;

public interface RecordService {

    ReadRecordDto findById(Long id) throws RowNotFoundException;

    Set<ReadRecordDto> findAll();

    ReadRecordDto create(CreateUpdateRecordDto recordDto) throws RowNotFoundException,
            UnavailableObjectException, RecordOccupiedTimeException;

    void deleteById(Long id) throws RowNotFoundException;
}
