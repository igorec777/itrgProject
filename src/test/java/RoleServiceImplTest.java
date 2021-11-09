import dto.role.RoleDto;
import exceptions.ConstraintViolationException;
import exceptions.RowNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import service.RoleService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceImplTest extends BaseServiceTest{

    private final RoleService roleService;
    private static final Long ROLES_COUNT = 4L;

    @Autowired
    RoleServiceImplTest(RoleService roleService) {
        this.roleService = roleService;
    }

    @Test
    void create_validData_shouldReturnCreatedRole() throws RowNotFoundException {
        RoleDto newRole = RoleDto.builder()
                .name("someName")
                .build();

        RoleDto createdRole = roleService.create(newRole);

        assertNotNull(createdRole);
        assertEquals(ROLES_COUNT + 1, createdRole.getId());
        assertEquals(newRole.getName(), createdRole.getName());

        System.out.println(createdRole);
    }


    @Test
    void findAll_shouldReturnSetOfRoles() {
        List<String> existRoles = List.of("Admin", "Worker", "Client", "Manager");
        Set<RoleDto> roles = roleService.findAll();

        assertNotNull(roles);
        assertEquals(ROLES_COUNT, roles.size());
        assertTrue(roles.stream().allMatch(r -> existRoles.contains(r.getName())));
        System.out.println(roles);
    }

    @Test
    void findById_validData_shouldReturnExistRole() throws RowNotFoundException {
        for (Long i = 1L; i <= ROLES_COUNT; i++) {
            RoleDto existRole = roleService.findById(i);
            assertNotNull(existRole);
            assertEquals(i, existRole.getId());
            assertNotNull(existRole.getName());
            System.out.println(existRole);
        }
    }

    @Test
    void findById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> roleService.findById(999L));
        assertThrows(RowNotFoundException.class, () -> roleService.findById(-53L));
        assertThrows(RowNotFoundException.class, () -> roleService.findById(0L));
    }

    //TODO fix
//    @Test
//    void update_validData_shouldPass() {
//        RoleDto roleDto = RoleDto.builder()
//                .id(1L)
//                .name("newName")
//                .build();
//        roleService.update(roleDto);
//        System.out.println("Deleted: " + roleDto);
//    }

    @Test
    void deleteById_validData_shouldPass() throws RowNotFoundException, ConstraintViolationException {

        RoleDto roleDto = RoleDto.builder()
                .id(4L)
                .build();
        roleService.deleteById(roleDto.getId());
        System.out.println("Deleted: " + roleDto);
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
        assertThrows(ConstraintViolationException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(1L)
                .build().getId()));
        assertThrows(ConstraintViolationException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(2L)
                .build().getId()));
        assertThrows(ConstraintViolationException.class, () -> roleService.deleteById(RoleDto.builder()
                .id(3L)
                .build().getId()));
    }
}