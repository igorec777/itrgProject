package service;

import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import exceptions.RestrictionViolationException;
import exceptions.RowNotFoundException;
import exceptions.UniqueRestrictionException;

import java.util.Set;

public interface PersonService {
    ReadPersonDto findById(Long id) throws RowNotFoundException;

    Set<ReadPersonDto> findAll();

    ReadPersonDto create(CreateUpdatePersonDto personDto) throws RowNotFoundException;

    void deleteById(Long id) throws RowNotFoundException, RestrictionViolationException;

    void update(CreateUpdatePersonDto personDto) throws RowNotFoundException, UniqueRestrictionException;
}
