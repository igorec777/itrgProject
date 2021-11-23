package app.service.impl;

import app.converter.role.RoleConverter;
import app.converter.role.impl.RoleConverterImpl;
import app.dao.RoleDao;
import app.dto.role.RoleDto;
import app.entity.Person;
import app.entity.Role;
import app.exception.ReferenceRestrictionException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;
import app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
