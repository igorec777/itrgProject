package service;

import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import exceptions.RowNotFoundException;

import java.util.Set;

public interface PersonService {
    ReadPersonDto findById(Long id) throws RowNotFoundException;

    Set<ReadPersonDto> findAll();

    ReadPersonDto create(CreateUpdatePersonDto personDto) throws RowNotFoundException;

    void deleteById(Long id) throws RowNotFoundException;

    //TODO fix
    void update(CreateUpdatePersonDto personDto) throws RowNotFoundException;
}
