package service.impl;

import converter.person.CreateUpdatePersonConverter;
import converter.person.ReadPersonConverter;
import converter.person.impl.CreateUpdatePersonConverterImpl;
import converter.person.impl.ReadPersonConverterImpl;
import dao.PersonDao;
import dao.RecordDao;
import dao.RoleDao;
import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import entity.Person;
import entity.Record;
import entity.Role;
import exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.PersonService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final RoleDao roleDao;
    private final RecordDao recordDao;
    private final ReadPersonConverter readPersonConverter;
    private final CreateUpdatePersonConverter createUpdatePersonConverter;

    private static final Long CLIENT_ROLE_ID = 1L;

    @Autowired
    PersonServiceImpl(PersonDao personDao, RoleDao roleDao, RecordDao recordDao, ReadPersonConverterImpl readPersonConverter,
                      CreateUpdatePersonConverterImpl createUpdatePersonConverter) {

        this.personDao = personDao;
        this.roleDao = roleDao;
        this.recordDao = recordDao;
        this.readPersonConverter = readPersonConverter;
        this.createUpdatePersonConverter = createUpdatePersonConverter;
    }

    @Override
    public ReadPersonDto findById(Long id) throws RowNotFoundException {
        return readPersonConverter.toDto(personDao.findById(id));
    }

    @Override
    public Set<ReadPersonDto> findAll() {
        return personDao.findAll().stream()
                .map(readPersonConverter::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ReadPersonDto create(CreateUpdatePersonDto personDto) throws UnavailableObjectException,
            UniqueRestrictionException, RowNotFoundException {

        if (personDto == null) {
            String exMessage = "'personDto' is unavailable";
            throw new UnavailableObjectException(exMessage);
        }
        if (isLoginNotUnique(personDto.getLogin())) {
            throw new UniqueRestrictionException(Person.class.getSimpleName() + " with login:" + personDto.getLogin() +
                    " already exist");
        }
        Person newPerson = createUpdatePersonConverter.fromDto(personDto);
        Role clientRole = roleDao.findById(CLIENT_ROLE_ID);
        newPerson.getRoles().add(clientRole);
        return readPersonConverter.toDto(personDao.create(newPerson));
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
        Person personToUpdate = createUpdatePersonConverter.fromDto(personDto);
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
