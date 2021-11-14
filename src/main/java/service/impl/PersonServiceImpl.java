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
import exceptions.ReferenceRestrictionException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;
import exceptions.UniqueRestrictionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.PersonService;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
            log.error(exMessage);
            throw new UnavailableObjectException(exMessage);
        }
        if (!isLoginUnique(personDto.getLogin())) {
            String exMessage = Person.class.getSimpleName() + " with login:" + personDto.getLogin() +
                    " already exist";
            log.error(exMessage);
            throw new UniqueRestrictionException(exMessage);
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
        boolean isAnyRecordHasPerson = existRecords.stream()
                .anyMatch(r -> r.getClient().getId().equals(id) || r.getWorker().getId().equals(id));

        if (isAnyRecordHasPerson) {
            String exMessage = "Cannot delete " + Person.class.getSimpleName() + " with id: " + id + " because" +
                    " some " + Record.class.getSimpleName() + "(s) references to this " + Person.class.getSimpleName();
            System.out.println("Log: " + exMessage);
            throw new ReferenceRestrictionException(exMessage);
        }
        personDao.delete(existPerson);
    }

    @Override
    public void update(CreateUpdatePersonDto personDto) throws RowNotFoundException, UniqueRestrictionException {
        Person personToUpdate = createUpdatePersonConverter.fromDto(personDto);
        //check is person exist and then
        //set roles to person because this method update only non reference fields
        personToUpdate.setRoles(personDao.findById(personDto.getId()).getRoles());
        //check if login value is unique
        Set<Person> existPeople = personDao.findAll();
        boolean isLoginNotUnique = existPeople.stream()
                .anyMatch(p -> p.getLogin().equals(personToUpdate.getLogin()));
        if (isLoginNotUnique) {
            String exMessage = Person.class.getSimpleName() + " with id:" + personToUpdate.getId() +
                    " restrict the unique key constraint on the field 'login'";
            System.out.println("Log: " + exMessage);
            throw new UniqueRestrictionException(exMessage);
        }
        personDao.update(personToUpdate);
    }

    private boolean isLoginUnique(String login) {
        Set<Person> people = personDao.findAll();
        return people.stream()
                .noneMatch(p -> login.equals(p.getLogin()));
    }
}
