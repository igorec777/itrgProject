package repository.jdbc;

import entity.jdbc.Person;
import org.junit.jupiter.api.Test;
import repository.jdbc.impl.PersonRepositoryImpl;
import repository.jdbc.impl.RoleRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonRepositoryImplTest extends BaseRepositoryTest {

    private final PersonRepository personRepository;

    private final List<Person> testPeople = List.of(
            new Person(1, "Name1", "Surname1", "Login1", "1111"),
            new Person(2, "Name2", "Surname2", "Login2", "2222"),
            new Person(3, "Name3", "Surname3", "Login3", "3333"));

    PersonRepositoryImplTest() {
        personRepository = new PersonRepositoryImpl(getConnectionPool());
    }

    @Test
    void readAll() {
        List<Person> resultPeople = personRepository.readAll();

        assertEquals(testPeople.size(), resultPeople.size());

        for (int i = 0; i < testPeople.size(); i++) {
            assertEquals(testPeople.get(i), resultPeople.get(i));
        }
    }

    @Test
    void readById() {
        for (int i = 1; i < testPeople.size(); i++) {
            assertEquals(testPeople.get(i - 1), personRepository.readById(i));
        }
    }

    @Test
    void create() {
        Person newPerson = new Person("Name4", "Surname4", "Login4", "4444");
        Person createdPerson = personRepository.create(newPerson, List.of(
                new RoleRepositoryImpl(getConnectionPool()).readById(1),
                new RoleRepositoryImpl(getConnectionPool()).readById(2)));

        assertEquals(newPerson.getName(), createdPerson.getName());
        assertEquals(newPerson.getSurname(), createdPerson.getSurname());
        assertEquals(newPerson.getLogin(), createdPerson.getLogin());
        assertEquals(newPerson.getPassword(), createdPerson.getPassword());
    }

    @Test
    void updateById() {
        Person updatedPerson = new Person("newName", "nameSurname", "newLogin", "newPassword");
        assertEquals(1, personRepository.updateById(3, updatedPerson));
    }

    @Test
    void deleteById() {
        int affectedRows;
        affectedRows = personRepository.deleteById(2);
        assertEquals(1, affectedRows);
        affectedRows = personRepository.deleteById(-5);
        assertEquals(0, affectedRows);
    }
}