import dto.person.ReadPersonDto;
import dto.service.ServiceDto;
import exceptions.RestrictionViolationException;
import exceptions.RowNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import service.ServiceManagementService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceManagementServiceImplTest extends BaseServiceTest {

    private final ServiceManagementService serviceManagementService;
    private static final Long SERVICES_COUNT = 3L;

    @Autowired
    ServiceManagementServiceImplTest(ServiceManagementService serviceManagementService) {
        this.serviceManagementService = serviceManagementService;
    }

    @Test
    void create_validData_shouldReturnCreatedService() throws RowNotFoundException {
        ServiceDto newService = ServiceDto.builder()
                .name("someName")
                .price(10.56)
                .build();

        ServiceDto createdService = serviceManagementService.create(newService);

        assertNotNull(createdService);
        assertEquals(SERVICES_COUNT + 1, createdService.getId());
        assertEquals(newService.getName(), createdService.getName());

        System.out.println(createdService);
    }


    @Test
    void findAll_shouldReturnSetOfRoles() {
        List<String> existServices = List.of("Peeling", "Pedicure", "Sugaring");
        Set<ServiceDto> services = serviceManagementService.findAll();

        assertNotNull(services);
        assertEquals(SERVICES_COUNT, services.size());
        assertTrue(services.stream().allMatch(r -> existServices.contains(r.getName())));
        System.out.println(services);
    }

    @Test
    void findById_validData_shouldReturnExistRole() throws RowNotFoundException {
        for (long i = 1L; i <= SERVICES_COUNT; i++) {
            ServiceDto existService = serviceManagementService.findById(i);
            assertNotNull(existService);
            assertEquals(i, existService.getId());
            assertNotNull(existService.getName());
            assertNotNull(existService.getPrice());
            System.out.println(existService);
        }
    }

    @Test
    void findById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.findById(999L));
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.findById(-53L));
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.findById(0L));
    }

    @Test
    void deleteById_validData_shouldPass() throws RowNotFoundException, RestrictionViolationException {
        ServiceDto serviceDto = ServiceDto.builder()
                .id(1L)
                .build();
        serviceManagementService.deleteById(serviceDto.getId());
        System.out.println("Deleted: " + serviceDto);
    }

    @Test
    void deleteById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.deleteById(ReadPersonDto.builder()
                .id(0L)
                .build().getId()));
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.deleteById(ReadPersonDto.builder()
                .id(-45L)
                .build().getId()));
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.deleteById(ReadPersonDto.builder()
                .id(95844L)
                .build().getId()));
    }

    @Test
    void deleteById_noSuitableData_shouldThrowException() {
        assertThrows(RestrictionViolationException.class, () -> serviceManagementService.deleteById(ReadPersonDto.builder()
                .id(2L)
                .build().getId()));
        assertThrows(RestrictionViolationException.class, () -> serviceManagementService.deleteById(ReadPersonDto.builder()
                .id(3L)
                .build().getId()));
    }
}
