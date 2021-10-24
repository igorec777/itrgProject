package repository;

import entity.Person;
import entity.Role;

import java.util.List;

public interface PersonRepository {

    Person create(Person person, List<Role> roles);

    List<Person> readAll();

    Person readById(int id);

    int updateById(int id, Person person);

    int deleteById(int id);
}
