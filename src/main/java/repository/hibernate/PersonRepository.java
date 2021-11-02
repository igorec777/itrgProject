package repository.hibernate;

import entity.hibernate.Person;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PersonRepository {

    Person create(Person person);

    Person addRoleByNameToPersonById(Long id, String name);

    Set<Person> readAll();

    Person readById(Long id);

    void updateById(Long id, Person person);

    void deleteById(Long id);
}
