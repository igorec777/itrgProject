package dao;

import entity.Person;
import exceptions.RowNotFoundException;

import java.util.Set;

public interface PersonDao {
    Person findById(Long id) throws RowNotFoundException;

    Set<Person> findAll();

    Person create(Person person);

    void delete(Person person);

    void update(Person person);
}
