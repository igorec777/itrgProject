package service;

import dto.role.RoleDto;
import exceptions.RestrictionViolationException;
import exceptions.RowNotFoundException;
import exceptions.UniqueRestrictionException;

import java.util.Set;

public interface RoleService {

    RoleDto findById(Long id) throws RowNotFoundException;

    Set<RoleDto> findAll();

    RoleDto create(RoleDto roleDto) throws RowNotFoundException;

    void update(RoleDto roleDto) throws RowNotFoundException, UniqueRestrictionException;

    void deleteById(Long id) throws RowNotFoundException, RestrictionViolationException;
}
