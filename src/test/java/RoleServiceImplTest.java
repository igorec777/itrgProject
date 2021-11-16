import dto.role.RoleDto;
import exceptions.ReferenceRestrictionException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;
import exceptions.UniqueRestrictionException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import service.RoleService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceImplTest extends BaseServiceTest {

    private final RoleService roleService;
    private static final Long ROLE_COUNT = 4L;
    private static final List<String> testRoleNames = List.of("Admin", "Worker", "Client", "Manager");

    @Autowired
    RoleServiceImplTest(RoleService roleService) {
        this.roleService = roleService;
    }

    @Test
    void create_validData_shouldReturnCreatedRole() throws RowNotFoundException,
            UniqueRestrictionException, UnavailableObjectException {

        RoleDto newRole = RoleDto.builder()
                .name("someName")
                .build();

        RoleDto createdRole = roleService.create(newRole);

        assertNotNull(createdRole);
        assertEquals(ROLE_COUNT + 1, createdRole.getId());
        assertEquals(newRole.getName(), createdRole.getName());
    }


    @Test
    void findAll_shouldReturnSetOfRoles() {
        Set<RoleDto> roles = roleService.findAll();

        assertNotNull(roles);
        assertEquals(ROLE_COUNT, roles.size());
        assertTrue(roles.stream().allMatch(r -> testRoleNames.contains(r.getName())));
    }

    @Test
    void findById_validData_shouldReturnExistRole() throws RowNotFoundException {
        for (long i = 1L; i <= ROLE_COUNT; i++) {
            RoleDto existRole = roleService.findById(i);
            assertNotNull(existRole);
            assertEquals(i, existRole.getId());
            assertNotNull(existRole.getName());
        }
    }

    @Test
    void findById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> roleService.findById(999L));
        assertThrows(RowNotFoundException.class, () -> roleService.findById(-53L));
        assertThrows(RowNotFoundException.class, () -> roleService.findById(0L));
    }

    @Test
    void deleteById_validData_shouldPass() throws RowNotFoundException, ReferenceRestrictionException {

        RoleDto roleDto = RoleDto.builder()
                .id(4L)
                .build();
        roleService.deleteById(roleDto.getId());
    }

    @Test
    void deleteById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(0L)
                .build().getId()));
        assertThrows(RowNotFoundException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(-45L)
                .build().getId()));
        assertThrows(RowNotFoundException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(95844L)
                .build().getId()));
    }

    @Test
    void deleteById_noSuitableData_shouldThrowException() {
        assertThrows(ReferenceRestrictionException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(1L)
                .build().getId()));
        assertThrows(ReferenceRestrictionException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(2L)
                .build().getId()));
        assertThrows(ReferenceRestrictionException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(3L)
                .build().getId()));
    }
}