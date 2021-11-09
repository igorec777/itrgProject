package service.impl;

import converter.role.ReadRoleConverterImpl;
import dao.PersonDao;
import dao.RoleDao;
import dto.role.RoleDto;
import entity.Person;
import entity.Role;
import exceptions.ConstraintViolationException;
import exceptions.RowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RoleService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final PersonDao personDao;
    private final ReadRoleConverterImpl readRoleConverter;


    @Autowired
    RoleServiceImpl(RoleDao roleDao, PersonDao personDao, ReadRoleConverterImpl readRoleConverter) {
        this.roleDao = roleDao;
        this.personDao = personDao;
        this.readRoleConverter = readRoleConverter;
    }

    @Override
    public RoleDto findById(Long id) throws RowNotFoundException {
        return readRoleConverter.toDto(roleDao.findById(id));
    }

    @Override
    public Set<RoleDto> findAll() {
        return roleDao.findAll().stream()
                .map(readRoleConverter::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public RoleDto create(RoleDto roleDto) throws RowNotFoundException {
        return readRoleConverter.toDto(roleDao.create(readRoleConverter.fromDto(roleDto)));
    }

    //TODO fix
    @Override
    public void update(RoleDto roleDto) {
        roleDao.update(readRoleConverter.fromDto(roleDto));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException, ConstraintViolationException {
        Role existRole = roleDao.findById(id);
        Set<Person> existPeople = personDao.findAll();
        if (isAnyPersonHasRole(existPeople, id)) {
            String msg = "Cannot delete role with id: " + id + ". Some person references to this role";
            System.out.println(msg);
            throw new ConstraintViolationException(msg);
        }
        roleDao.delete(existRole);
    }

    private boolean isAnyPersonHasRole(Set<Person> people, Long id) {
        for (Person p : people) {
            for (Role r : p.getRoles()) {
                if (id.equals(r.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
