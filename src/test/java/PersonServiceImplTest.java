import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import dto.record.ReadRecordDto;
import dto.role.RoleDto;
import entity.Person;
import exceptions.RestrictionViolationException;
import exceptions.RowNotFoundException;
import exceptions.UniqueRestrictionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import service.PersonService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceImplTest extends BaseServiceTest {

    private final PersonService personService;
    private static final Long PERSON_COUNT = 3L;

    @Autowired
    PersonServiceImplTest(PersonService personService) {
        this.personService = personService;
    }

    @Test
    void create_validData_shouldReturnCreatedPerson() throws RowNotFoundException {
        CreateUpdatePersonDto newPerson = CreateUpdatePersonDto.builder()
                .name("newName")
                .surname("newSurname")
                .login("newLogin")
                .password("1111")
                .build();

        ReadPersonDto createdPerson = personService.create(newPerson);

        assertNotNull(createdPerson);
        assertEquals(newPerson.getName(), createdPerson.getName());
        assertEquals(newPerson.getSurname(), createdPerson.getSurname());
        assertEquals(newPerson.getLogin(), createdPerson.getLogin());

        assertNotNull(createdPerson.getRoles());

        System.out.println(createdPerson);
    }

    @Test
    void create_wrongData_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> personService.create(null));
    }

    @Test
    void findAll_shouldReturnSetOfPeople() {
        Set<ReadPersonDto> people = personService.findAll();
        assertNotNull(people);
        assertEquals(3, people.size());
        System.out.println(people);
    }

    @Test
    void findById_validData_shouldReturnExistPerson() throws RowNotFoundException {
        for (long i = 1; i <= PERSON_COUNT; i++) {
            ReadPersonDto existPerson = personService.findById(i);
            assertNotNull(existPerson);
            assertEquals(i, existPerson.getId());
            assertNotNull(existPerson.getName());
            assertNotNull(existPerson.getSurname());
            assertNotNull(existPerson.getLogin());
            assertNotNull(existPerson.getRoles());
            System.out.println(existPerson);
        }
    }

    @Test
    void findById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> personService.findById(999L));
        assertThrows(RowNotFoundException.class, () -> personService.findById(-53L));
        assertThrows(RowNotFoundException.class, () -> personService.findById(0L));
    }

    @Test
    void update_validData_shouldPass() throws RowNotFoundException, UniqueRestrictionException {
        CreateUpdatePersonDto updatedPerson = CreateUpdatePersonDto.builder()
                        .id(2L)
                        .name("someName")
                        .surname("someSurname")
                        .login("someLogin")
                        .password("1111")
                        .build();
        personService.update(updatedPerson);
    }

    @Test
    void update_noSuitableData_shouldThrowException() {
        CreateUpdatePersonDto updatedPerson = CreateUpdatePersonDto.builder()
                .id(1L)
                .name("someName")
                .surname("someSurname")
                //person already exist with this login, should throw exception
                .login("Login2")
                .password("1111")
                .build();
        assertThrows(UniqueRestrictionException.class, () -> personService.update(updatedPerson));
    }

    @Test
    void deleteById_validData_shouldPass() throws RowNotFoundException, RestrictionViolationException {
        ReadPersonDto personDto = ReadPersonDto.builder()
                .id(3L)
                .build();
        personService.deleteById(personDto.getId());
        System.out.println("Deleted: " + personDto);
    }

    @Test
    void deleteById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> personService.deleteById(ReadPersonDto.builder()
                .id(0L)
                .build().getId()));
        assertThrows(RowNotFoundException.class, () -> personService.deleteById(ReadPersonDto.builder()
                .id(-45L)
                .build().getId()));
        assertThrows(RowNotFoundException.class, () -> personService.deleteById(ReadPersonDto.builder()
                .id(95844L)
                .build().getId()));
    }

    @Test
    void deleteById_noSuitableData_shouldThrowException() {
        assertThrows(RestrictionViolationException.class, () -> personService.deleteById(ReadPersonDto.builder()
                .id(1L)
                .build().getId()));
        assertThrows(RestrictionViolationException.class, () -> personService.deleteById(ReadPersonDto.builder()
                .id(2L)
                .build().getId()));
    }
}