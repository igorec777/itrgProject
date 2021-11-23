package app.service;

import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.exception.*;

import java.util.Set;

public interface PersonService {
    ReadPersonDto findById(Long id) throws RowNotFoundException, RowNotFoundException;

    Set<ReadPersonDto> findAll();

    ReadPersonDto create(CreateUpdatePersonDto personDto) throws UnavailableObjectException, UniqueRestrictionException, RowNotFoundException, UnavailableObjectException;

    void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException;

    void update(CreateUpdatePersonDto personDto) throws RowNotFoundException, UniqueRestrictionException;

    void setRole(Long personId, Long roleId) throws RowNotFoundException, UnableToUpdateException;

    void removeRole(Long personId, Long roleId) throws UnableToUpdateException, RowNotFoundException;
}
