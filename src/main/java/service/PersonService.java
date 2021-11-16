package service;

import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import exceptions.*;

import java.util.Set;

public interface PersonService {
    ReadPersonDto findById(Long id) throws RowNotFoundException;

    Set<ReadPersonDto> findAll();

    ReadPersonDto create(CreateUpdatePersonDto personDto) throws UnavailableObjectException, UniqueRestrictionException, RowNotFoundException;

    void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException;

    void update(CreateUpdatePersonDto personDto) throws RowNotFoundException, UniqueRestrictionException;

    void setRole(Long personId, Long roleId) throws RowNotFoundException, UnableToUpdateException;

    void removeRole(Long personId, Long roleId) throws UnableToUpdateException, RowNotFoundException;
}
