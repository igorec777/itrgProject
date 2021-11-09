package service;

import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import dto.role.RoleDto;
import exceptions.ConstraintViolationException;
import exceptions.RowNotFoundException;

import java.util.Set;

public interface RoleService {

    RoleDto findById(Long id) throws RowNotFoundException;

    Set<RoleDto> findAll();

    RoleDto create(RoleDto roleDto) throws RowNotFoundException;

    //TODO fix
    void update(RoleDto roleDto);

    void deleteById(Long id) throws RowNotFoundException, ConstraintViolationException;
}
