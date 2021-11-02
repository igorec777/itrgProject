package repository.hibernate;

import entity.hibernate.BaseEntity;
import entity.hibernate.Person;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PersonRepositoryImplTest extends BaseRepositoryTest {

    private final Set<Person> testPeople;

    PersonRepositoryImplTest() {
        testPeople = new TreeSet<>(Comparator.comparing(BaseEntity::getId));
        testPeople.add(new Person(1L, "Name1", "Surname1", "Login1", "1111"));
        testPeople.add(new Person(2L, "Name2", "Surname2", "Login2", "2222"));
        testPeople.add(new Person(3L, "Name3", "Surname3", "Login3", "3333"));
    }

    @Test
    void create_validData_shouldReturnCreatedPerson() {
        Person newPerson = new Person("newName", "newSurname", "newLogin", "newPassword");
        assertEquals(newPerson, personRepository.create(newPerson));
    }

    @Test
    void addRoleByNameToPersonById_validData_shouldReturnUpdatedPerson() {
        Person resultPerson;
        //2 roles was -> 3 after add()
        resultPerson = personRepository.addRoleByNameToPersonById(1L, "Client");
        assertEquals(3, resultPerson.getRoles().size());
    }

    @Test
    void addRoleByNameToPersonById_wrongData_shouldReturnNull() {
        assertNull(personRepository.addRoleByNameToPersonById(1L, "notExistRole"));
    }

    @Test
    void addRoleByNameToPersonById_noSuitableData_shouldReturnSamePerson() {
        Person resultPerson;
        //2 roles was -> 2 after add() because already has role 'Admin'
        resultPerson = personRepository.addRoleByNameToPersonById(1L, "Admin");
        assertEquals(2, resultPerson.getRoles().size());
    }

    @Test
    void readAll_shouldReturnExistSetOfPeople() {
        Set<Person> resultPeople = new TreeSet<>(Comparator.comparing(BaseEntity::getId));
        resultPeople.addAll(personRepository.readAll());

        assertEquals(testPeople.size(), resultPeople.size());
        assertEquals(testPeople, resultPeople);
    }

    @Test
    void readById_validData_shouldReturnExistPerson() {
        Set<Person> resultPeople = new TreeSet<>(Comparator.comparing(BaseEntity::getId));

        for (long i = 0; i < testPeople.size(); i++) {
            resultPeople.add(personRepository.readById(i + 1));
        }
        assertEquals(testPeople.size(), resultPeople.size());
        assertEquals(testPeople, resultPeople);
    }

    @Test
    void readById_wrongData_shouldReturnNull() {
        assertNull(personRepository.readById(20000L));
        assertNull(personRepository.readById(0L));
        assertNull(personRepository.readById(-24L));
    }

    @Test
    void updateById_validData_shouldPass() {
        personRepository.updateById(1L,
                new Person("newName", "newSurname", "newLogin", "newPassword"));
    }

    @Test
    void deleteById_validData_shouldReturnNothing() {
        personRepository.deleteById(1L);
        personRepository.deleteById(2L);
        personRepository.deleteById(3L);
    }

    @Test
    void deleteById_wrongData_shouldReturnNothing() {
        personRepository.deleteById(-15L);
        personRepository.deleteById(0L);
        personRepository.deleteById(39584L);
    }
}