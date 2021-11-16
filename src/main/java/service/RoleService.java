package service;

import dto.role.RoleDto;
import exceptions.ReferenceRestrictionException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;
import exceptions.UniqueRestrictionException;

import java.util.Set;

public interface RoleService {

    RoleDto findById(Long id) throws RowNotFoundException;

    Set<RoleDto> findAll();

    RoleDto create(RoleDto roleDto) throws RowNotFoundException, UnavailableObjectException, UniqueRestrictionException;

    void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException;
}
