package repository.hibernate;

import entity.hibernate.Person;
import entity.hibernate.Record;
import entity.hibernate.Role;
import entity.hibernate.Service;
import org.junit.jupiter.api.Test;
import repository.hibernate.impl.PersonRepositoryImpl;
import repository.hibernate.impl.RecordRepositoryImpl;
import repository.hibernate.impl.RoleRepositoryImpl;
import repository.hibernate.impl.ServiceRepositoryImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidDataScenarioTest extends BaseRepositoryTest {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final RecordRepository recordRepository;
    private final ServiceRepository serviceRepository;

    ValidDataScenarioTest() {
        personRepository = new PersonRepositoryImpl(getSessionFactory());
        roleRepository = new RoleRepositoryImpl(getSessionFactory());
        recordRepository = new RecordRepositoryImpl(getSessionFactory());
        serviceRepository = new ServiceRepositoryImpl(getSessionFactory());
    }

    @Test
    void scenario_validData_shouldPass() {

        //TODO turn off V3 migration (with filling tables)

        //create 4 roles
        Role adminRole = roleRepository.create(new Role("Admin"));
        Role clientRole = roleRepository.create(new Role("Client"));
        Role workerRole = roleRepository.create(new Role("Worker34123"));
        Role excessRole = roleRepository.create(new Role("ExcessSole"));

        //roles should be 4
        assertEquals(4, roleRepository.readAll().size());

        //delete excess role
        roleRepository.deleteById(excessRole.getId());

        //roles should be 3
        assertEquals(3, roleRepository.readAll().size());

        //correct name of the role worker to 'Worker'
        roleRepository.updateById(workerRole.getId(), new Role("Worker"));

        //check that role worker now with correct name
        assertEquals("Worker", roleRepository.readByName("Worker").getName());

        //create 4 services
        Service peeling = serviceRepository.create(new Service("Peeling", 0.0));
        Service sugaring = serviceRepository.create(new Service("Sugaring", 20.0));
        Service pedicure = serviceRepository.create(new Service("Pedicure", 30.0));
        Service excessService = serviceRepository.create(new Service("Excess", 60.0));

        //services should be 4
        assertEquals(4, serviceRepository.readAll().size());

        //delete excess service
        serviceRepository.deleteById(excessService.getId());

        //services should be 3
        assertEquals(3, serviceRepository.readAll().size());

        //set price of peeling service
        peeling.setPrice(peeling.getPrice() + 15.0);
        serviceRepository.updateById(peeling.getId(), peeling);

        //check that price of peeling is correct
        assertEquals(15.0, serviceRepository.readById(peeling.getId()).getPrice());

        //create 4 people (admin,client,worker, excess)
        Person admin = personRepository.create(new Person("admin", "surname", "root", "1111"));
        Person client = personRepository.create(new Person("client", "surname", "cl1", "5453"));
        Person worker = personRepository.create(new Person("worker", "surname", "wk1", "3333"));
        Person excessPerson = personRepository.create(new Person("", "", "", ""));

        //delete excess person
        personRepository.deleteById(excessPerson.getId());

        //change password for client
        client.setPassword("2222");
        personRepository.updateById(client.getId(), client);

        //check that password of client is correct
        assertEquals("2222", personRepository.readById(client.getId()).getPassword());

        //give admin role 'Admin'
        personRepository.addRoleByNameToPersonById(admin.getId(), "Admin");

        //give client role 'Client'
        personRepository.addRoleByNameToPersonById(client.getId(), "Client");

        //give worker role 'Worker'
        personRepository.addRoleByNameToPersonById(worker.getId(), "Worker");

        //read people and check their roles
        personRepository.readAll().forEach(System.out::println);

        //create 3 records
        recordRepository.create(new Record(
                Date.valueOf(LocalDate.of(2021, 10, 26)),
                Timestamp.valueOf("2021-10-26 12:20:00"),
                Timestamp.valueOf("2021-10-26 14:20:00")
        ), client.getId(), worker.getId(), peeling.getId());

        recordRepository.create(new Record(
                Date.valueOf(LocalDate.of(2021, 10, 30)),
                Timestamp.valueOf("2021-10-30 18:20:00"),
                Timestamp.valueOf("2021-10-30 20:40:00")
        ), client.getId(), admin.getId(), sugaring.getId());

        recordRepository.create(new Record(
                Date.valueOf(LocalDate.of(2021, 11, 6)),
                Timestamp.valueOf("2021-11-6 8:50:00"),
                Timestamp.valueOf("2021-11-6 10:00:00")
        ), worker.getId(), admin.getId(), pedicure.getId());

        //records should be 3
        assertEquals(3, recordRepository.readAll().size());

        //delete service sugaring (record with this service also should be deleted)
        serviceRepository.deleteById(sugaring.getId());

        //services should be 2 and records should be 2
        assertEquals(2, serviceRepository.readAll().size());
        assertEquals(2, recordRepository.readAll().size());

        //delete person admin (records with this person should be also deleted)
        personRepository.deleteById(admin.getId());

        //people should be 2 and records should be 1
        assertEquals(2, personRepository.readAll().size());
        assertEquals(1, recordRepository.readAll().size());

        //delete person worker (records with this person should be also deleted)
        personRepository.deleteById(worker.getId());

        //people should be 1 and records should be 0
        assertEquals(1, personRepository.readAll().size());
        assertEquals(0, recordRepository.readAll().size());
    }

}
