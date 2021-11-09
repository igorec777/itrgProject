package service.impl;

import converter.role.RoleConverterImpl;
import dao.PersonDao;
import dao.RoleDao;
import dto.role.RoleDto;
import entity.Person;
import entity.Role;
import exceptions.RestrictionViolationException;
import exceptions.RowNotFoundException;
import exceptions.UniqueRestrictionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RoleService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final PersonDao personDao;
    private final RoleConverterImpl roleConverter;


    @Autowired
    RoleServiceImpl(RoleDao roleDao, PersonDao personDao, RoleConverterImpl roleConverter) {
        this.roleDao = roleDao;
        this.personDao = personDao;
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
    public RoleDto create(RoleDto roleDto) throws RowNotFoundException {
        return roleConverter.toDto(roleDao.create(roleConverter.fromDto(roleDto)));
    }

    @Override
    public void update(RoleDto roleDto) throws RowNotFoundException, UniqueRestrictionException {
        //check is role exist and then
        //set people to role because this method update only non reference fields
        Role roleToUpdate = roleConverter.fromDto(roleDto);
        roleToUpdate.setPeople(roleDao.findById(roleDto.getId()).getPeople());
        //check if name value is unique
        Set<Role> existRoles = roleDao.findAll();
        boolean isNameNotUnique = existRoles.stream()
                .anyMatch(p -> p.getName().equals(roleToUpdate.getName()));
        if (isNameNotUnique) {
            String exMessage = Role.class.getSimpleName() + " with id:" + roleToUpdate.getId() +
                    " restrict the unique key constraint on the field 'name'";
            System.out.println("Log: " + exMessage);
            throw new UniqueRestrictionException(exMessage);
        }
        roleDao.update(roleToUpdate);
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException, RestrictionViolationException {
        Role existRole = roleDao.findById(id);
        Set<Person> existPeople = personDao.findAll();
        if (isAnyPersonHasRole(existPeople, id)) {
            String exMessage = "Cannot delete " + Role.class.getSimpleName() + " with id: " + id + " because" +
                    " some " + Person.class.getSimpleName() + "(s) references to this " + Role.class.getSimpleName();
            System.out.println("Log: " + exMessage);
            throw new RestrictionViolationException(exMessage);
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
