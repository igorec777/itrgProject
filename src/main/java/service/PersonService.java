package service;

import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import exceptions.ReferenceRestrictionException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;
import exceptions.UniqueRestrictionException;

import java.util.Set;

public interface PersonService {
    ReadPersonDto findById(Long id) throws RowNotFoundException;

    Set<ReadPersonDto> findAll();

    ReadPersonDto create(CreateUpdatePersonDto personDto) throws UnavailableObjectException, UniqueRestrictionException, RowNotFoundException;

    //todo
    void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException;

    //todo
    void update(CreateUpdatePersonDto personDto) throws RowNotFoundException, UniqueRestrictionException;
}
