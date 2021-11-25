package app.controller;

import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.dto.record.CreateUpdateRecordDto;
import app.dto.record.ReadRecordDto;
import app.dto.service.ServiceDto;
import app.exception.*;
import app.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/all")
    Set<ReadRecordDto> findAll() {
        return recordService.findAll();
    }

    @GetMapping(value = "/get/{id}")
    ReadRecordDto findById(@PathVariable Long id) throws RowNotFoundException {
        return recordService.findById(id);
    }

    @PostMapping("/add")
    ReadRecordDto create(@Valid @RequestBody CreateUpdateRecordDto record) throws RowNotFoundException,
            UnavailableObjectException, RecordOccupiedTimeException {
        return recordService.create(record);
    }

    @DeleteMapping("/delete/{id}")
    HttpStatus deleteById(@PathVariable Long id) throws RowNotFoundException {
        recordService.deleteById(id);
        return HttpStatus.OK;
    }
}
