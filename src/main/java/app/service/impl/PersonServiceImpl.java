package app.service.impl;

import app.converter.person.PersonConverter;
import app.dao.PersonDao;
import app.dao.RecordDao;
import app.dao.RoleDao;
import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.entity.Person;
import app.entity.Record;
import app.entity.Role;
import app.exception.*;
import app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final RoleDao roleDao;
    private final RecordDao recordDao;
    private final PersonConverter personConverter;

    private static final Long CLIENT_ROLE_ID = 1L;

    @Autowired
    PersonServiceImpl(PersonDao personDao, RoleDao roleDao, RecordDao recordDao, PersonConverter personConverter) {

        this.personDao = personDao;
        this.roleDao = roleDao;
        this.recordDao = recordDao;
        this.personConverter = personConverter;
    }

    @Override
    public ReadPersonDto findById(Long id) throws RowNotFoundException {
        return personConverter.toReadPersonDto(personDao.findById(id));
    }

    @Override
    public Set<ReadPersonDto> findAll() {
        return personDao.findAll().stream()
                .map(personConverter::toReadPersonDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ReadPersonDto create(CreateUpdatePersonDto personDto) throws
            UniqueRestrictionException, RowNotFoundException, UnavailableObjectException {

        if (personDto == null) {
            String exMessage = "'personDto' is unavailable";
            throw new UnavailableObjectException(exMessage);
        }
        if (isLoginNotUnique(personDto.getLogin())) {
            throw new UniqueRestrictionException(Person.class.getSimpleName() + " with login:" + personDto.getLogin() +
                    " already exist");
        }
        Person newPerson = personConverter.fromCreateUpdatePersonDto(personDto);
        Role clientRole = roleDao.findById(CLIENT_ROLE_ID);
        newPerson.getRoles().add(clientRole);
        return personConverter.toReadPersonDto(personDao.create(newPerson));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException {
        Person existPerson = personDao.findById(id);
        Set<Record> existRecords = recordDao.findAll();

        if (isAnyRecordHasPerson(existRecords, id)) {
            throw new ReferenceRestrictionException("Some of " + Record.class.getSimpleName() + " references to "
                    + Person.class.getSimpleName() + " with id:" + id);
        }
        personDao.delete(existPerson);
    }

    @Override
    public void update(CreateUpdatePersonDto personDto) throws RowNotFoundException, UniqueRestrictionException {
        Person personToUpdate = personConverter.fromCreateUpdatePersonDto(personDto);
        //check is person exist and then
        //set roles to person because this method update only non reference fields
        personToUpdate.setRoles(personDao.findById(personDto.getId()).getRoles());

        if (isLoginNotUnique(personToUpdate.getLogin())) {
            throw new UniqueRestrictionException(Person.class.getSimpleName() + " with login:" + personDto.getLogin() +
                    " already exist");
        }
        personDao.update(personToUpdate);
    }

    @Override
    public void setRole(Long personId, Long roleId) throws RowNotFoundException, UnableToUpdateException {
        Person personToUpdate = personDao.findById(personId);
        Role existRole = roleDao.findById(roleId);

        if (isPersonHasRole(personToUpdate, existRole)) {
            throw new UnableToUpdateException(Role.class.getSimpleName() + " with id:" + roleId + " already exist");
        }
        else {
            personToUpdate.getRoles().add(existRole);
            personDao.update(personToUpdate);
        }
    }

    @Override
    public void removeRole(Long personId, Long roleId) throws UnableToUpdateException, RowNotFoundException {
        Person personToUpdate = personDao.findById(personId);

        if (personToUpdate.getRoles().removeIf(r -> roleId.equals(r.getId()))) {
            personDao.update(personToUpdate);
        }
        else {
            throw new UnableToUpdateException(Person.class.getSimpleName() + " with id:" + personId
                    + " hasn't role with id:" + roleId);
        }
    }

    private boolean isLoginNotUnique(String login) {
        Set<Person> people = personDao.findAll();
        return people.stream()
                .anyMatch(p -> login.equals(p.getLogin()));
    }

    private boolean isAnyRecordHasPerson(Set<Record> records, Long id) {
        return records.stream()
                .anyMatch(r -> r.getClient().getId().equals(id) || r.getWorker().getId().equals(id));
    }

    private boolean isPersonHasRole(Person person, Role role) {
        return person.getRoles().stream()
                .anyMatch(r -> role.getId().equals(r.getId()));
    }
}
