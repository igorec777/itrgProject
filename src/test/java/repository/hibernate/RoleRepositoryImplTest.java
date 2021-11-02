package repository.hibernate;

import entity.hibernate.BaseEntity;
import entity.hibernate.Role;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleRepositoryImplTest extends BaseRepositoryTest{

    private final Set<Role> testRoles;

    RoleRepositoryImplTest() {
        testRoles = new TreeSet<>(Comparator.comparing(BaseEntity::getId));
        testRoles.add(new Role(1L,"Admin"));
        testRoles.add(new Role(2L,"Worker"));
        testRoles.add(new Role(3L,"Client"));
    }

    @Test
    void readAll_shouldReturnExistSetOfRoles() {
        Set<Role> resultRoles = new TreeSet<>(Comparator.comparing(BaseEntity::getId));
        resultRoles.addAll(roleRepository.readAll());

        assertEquals(testRoles.size(), resultRoles.size());
        assertEquals(testRoles, resultRoles);
    }

    @Test
    void readByName_validData_shouldReturnExistRole() {
        Set<Role> resultRoles = new TreeSet<>(Comparator.comparing(BaseEntity::getId));

        testRoles.forEach(tr -> resultRoles.add(roleRepository.readByName(tr.getName())));

        assertEquals(testRoles.size(), resultRoles.size());
        assertEquals(testRoles, resultRoles);
    }

    @Test
    void create_validData_shouldReturnCreatedRole() {
        Role newRole = new Role("newName");
        assertEquals(newRole, roleRepository.create(newRole));
    }

    @Test
    void updateById_validData_shouldPass() {
        roleRepository.updateById(1L, new Role("newRoles"));
    }

    @Test
    void deleteById_validData_shouldReturnNothing() {
        roleRepository.deleteById(1L);
        roleRepository.deleteById(2L);
        roleRepository.deleteById(3L);
    }

    @Test
    void deleteById_wrongData_shouldReturnNothing() {
        roleRepository.deleteById(-15L);
        roleRepository.deleteById(0L);
        roleRepository.deleteById(39584L);
    }
}