import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.exception.*;
import app.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    void create_validData_shouldReturnCreatedPerson() throws RowNotFoundException,
            UnavailableObjectException, UniqueRestrictionException {

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
    }

    @Test
    void create_wrongData_shouldThrowException() {
        assertThrows(UnavailableObjectException.class, () -> personService.create(null));
    }

    @Test
    void create_wrongData_shouldThrowException2() {
        CreateUpdatePersonDto newPerson = CreateUpdatePersonDto.builder()
                .name("newName")
                .surname("newSurname")
                //person with this login already exist
                .login("Login1")
                .password("1111")
                .build();
        assertThrows(UniqueRestrictionException.class, () -> personService.create(newPerson));
    }

    @Test
    void findAll_shouldReturnSetOfPeople() {
        Set<ReadPersonDto> people = personService.findAll();
        assertNotNull(people);
        assertEquals(3, people.size());
        people.forEach(p -> assertNotNull(p.getRoles()));
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
    void deleteById_validData_shouldPass() throws RowNotFoundException, ReferenceRestrictionException {
        ReadPersonDto personDto = ReadPersonDto.builder()
                .id(3L)
                .build();
        personService.deleteById(personDto.getId());
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
        assertThrows(ReferenceRestrictionException.class, () -> personService.deleteById(ReadPersonDto.builder()
                .id(1L)
                .build().getId()));
        assertThrows(ReferenceRestrictionException.class, () -> personService.deleteById(ReadPersonDto.builder()
                .id(2L)
                .build().getId()));
    }

    //setRole
    @Test
    void setRole_validData_shouldPass() throws RowNotFoundException, UnableToUpdateException {
        personService.setRole(1L, 3L);
    }

    @Test
    void setRole_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> personService.setRole(243412L, 3L));
        assertThrows(RowNotFoundException.class, () -> personService.setRole(1L, 243412L));
    }

    @Test
    void setRole_existRole_shouldThrowException() throws RowNotFoundException, UnableToUpdateException {
        assertThrows(UnableToUpdateException.class, () -> personService.setRole(1L, 1L));
    }

    @Test
    void removeRole_validData_shouldPass() throws RowNotFoundException, UnableToUpdateException {
        personService.removeRole(1L, 1L);
    }

    @Test
    void setRole_noExistRole_shouldThrowException() {
        assertThrows(UnableToUpdateException.class, () -> personService.removeRole(1L, 34324L));
    }


}