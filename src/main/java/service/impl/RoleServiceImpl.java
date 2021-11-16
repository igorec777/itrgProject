package service.impl;

import converter.role.RoleConverter;
import converter.role.impl.RoleConverterImpl;
import dao.PersonDao;
import dao.RoleDao;
import dto.role.RoleDto;
import entity.Person;
import entity.Role;
import exceptions.ReferenceRestrictionException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;
import exceptions.UniqueRestrictionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RoleService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final RoleConverter roleConverter;


    @Autowired
    RoleServiceImpl(RoleDao roleDao, RoleConverterImpl roleConverter) {
        this.roleDao = roleDao;
        this.roleConverter = roleConverter;
    }

    @Override
    public RoleDto findById(Long id) throws RowNotFoundException {
        return roleConverter.toDto(roleDao.findById(id));
    }

    @Override
    public Set<RoleDto> findAll() {
        return roleDao.findAll().stream()
                .map(roleConverter::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public RoleDto create(RoleDto roleDto) throws RowNotFoundException, UnavailableObjectException,
            UniqueRestrictionException {
        if (roleDto == null) {
            throw new UnavailableObjectException("'roleDto' is unavailable");
        }
        if (isNameNotUnique(roleDto.getName())) {
            throw new UniqueRestrictionException(Role.class.getSimpleName() + " with name:" + roleDto.getName() +
                    " already exist");
        }
        Role newRole = roleConverter.fromDto(roleDto);
        return roleConverter.toDto(roleDao.create(newRole));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException {
        Role existRole = roleDao.findById(id);
        //check if some person has this role
        if (!existRole.getPeople().isEmpty()) {
            throw new ReferenceRestrictionException("Some " + Person.class.getSimpleName() + " references to "
            + Role.class.getSimpleName() + " with id:" + id);
        }
        roleDao.delete(existRole);
    }

    private boolean isNameNotUnique(String name) {
        Set<Role> roles = roleDao.findAll();
        return roles.stream()
                .anyMatch(r -> name.equals(r.getName()));
    }
}
