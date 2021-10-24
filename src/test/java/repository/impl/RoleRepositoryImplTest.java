package repository.impl;

import entity.Person;
import entity.Role;
import org.junit.jupiter.api.Test;
import repository.BaseRepositoryTest;
import repository.RoleRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepositoryImplTest extends BaseRepositoryTest {

    private final RoleRepository roleRepository;

    private final List<Role> testRoles = List.of(
            new Role(1, "ADMIN"),
            new Role(2, "WORKER"),
            new Role(3, "CLIENT"));

    RoleRepositoryImplTest() {
        roleRepository = new RoleRepositoryImpl(getConnectionPool());
    }

    @Test
    void create() {
        Role newRole = new Role(4,"NewRole");
        assertEquals(newRole, roleRepository.create(newRole));
    }

    @Test
    void readById() {
        for (int i = 1; i < testRoles.size(); i++) {
            assertEquals(testRoles.get(i - 1), roleRepository.readById(i));
        }
    }

    @Test
    void readAll() {
        List<Role> resultRoles = roleRepository.readAll();

        assertEquals(testRoles.size(), resultRoles.size());

        for (int i = 0; i < testRoles.size(); i++) {
            assertEquals(testRoles.get(i), resultRoles.get(i));
        }
    }

    @Test
    void updateById() {
        Role updatedRole = new Role("newRole");
        assertEquals(1, roleRepository.updateById(2, updatedRole));
    }

    @Test
    void deleteById() {
        int affectedRows;
        affectedRows = roleRepository.deleteById(2);
        assertEquals(1, affectedRows);
        affectedRows = roleRepository.deleteById(-5);
        assertEquals(0, affectedRows);
    }
}