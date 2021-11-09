package service.impl;

import converter.person.CreateUpdatePersonConverterImpl;
import converter.person.ReadPersonConverterImpl;
import dao.PersonDao;
import dao.RoleDao;
import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import entity.Person;
import entity.Role;
import exceptions.RowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.PersonService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final RoleDao roleDao;
    private final ReadPersonConverterImpl readPersonConverter;
    private final CreateUpdatePersonConverterImpl createUpdatePersonConverter;

    private static final Long CLIENT_ROLE_ID = 1L;

    @Autowired
    PersonServiceImpl(PersonDao personDao, RoleDao roleDao, ReadPersonConverterImpl readPersonConverter,
                      CreateUpdatePersonConverterImpl createUpdatePersonConverter) {

        this.personDao = personDao;
        this.roleDao = roleDao;
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
    public ReadPersonDto create(CreateUpdatePersonDto personDto) throws RowNotFoundException {
        Person person;
        try {
            person = createUpdatePersonConverter.fromDto(personDto);
        } catch (NullPointerException ex) {
            String msg = "'personDto' is null";
            System.out.println(msg);
            throw new NullPointerException(msg);
        }
        Role clientRole = roleDao.findById(CLIENT_ROLE_ID);
        person.getRoles().add(clientRole);
        return readPersonConverter.toDto(personDao.create(person));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException {
        Person existPerson = personDao.findById(id);
        personDao.delete(existPerson);
    }

    //TODO fix
    @Override
    public void update(CreateUpdatePersonDto personDto) throws RowNotFoundException {
        //check is person exist
        personDao.findById(personDto.getId());
        personDao.update(createUpdatePersonConverter.fromDto(personDto));
    }
}
