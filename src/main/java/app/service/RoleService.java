package app.service;

import app.dto.role.RoleDto;
import app.exception.ReferenceRestrictionException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;

import java.util.Set;

public interface RoleService {

    RoleDto findById(Long id) throws RowNotFoundException;

    Set<RoleDto> findAll();

    RoleDto create(RoleDto roleDto) throws RowNotFoundException, UnavailableObjectException, UniqueRestrictionException;

    void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException;
}
