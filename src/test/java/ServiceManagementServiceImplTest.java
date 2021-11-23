import app.dto.person.ReadPersonDto;
import app.dto.service.ServiceDto;
import app.exception.ReferenceRestrictionException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;
import app.service.ServiceManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceManagementServiceImplTest extends BaseServiceTest {

    private final ServiceManagementService serviceManagementService;
    private static final Long SERVICES_COUNT = 3L;

    @Autowired
    ServiceManagementServiceImplTest(ServiceManagementService serviceManagementService) {
        this.serviceManagementService = serviceManagementService;
    }

    @Test
    void create_validData_shouldReturnCreatedService() throws UniqueRestrictionException, UnavailableObjectException {
        ServiceDto newService = ServiceDto.builder()
                .name("someName")
                .price(10.56)
                .build();

        ServiceDto createdService = serviceManagementService.create(newService);

        assertNotNull(createdService);
        assertEquals(SERVICES_COUNT + 1, createdService.getId());
        assertEquals(newService.getName(), createdService.getName());
        assertEquals(newService.getPrice(), createdService.getPrice());
    }


    @Test
    void findAll_shouldReturnSetOfRoles() {
        List<String> existServices = List.of("Peeling", "Pedicure", "Sugaring");
        Set<ServiceDto> services = serviceManagementService.findAll();

        assertNotNull(services);
        assertEquals(SERVICES_COUNT, services.size());
        assertTrue(services.stream().allMatch(r -> existServices.contains(r.getName())));
    }

    @Test
    void findById_validData_shouldReturnExistRole() throws RowNotFoundException {
        for (long i = 1L; i <= SERVICES_COUNT; i++) {
            ServiceDto existService = serviceManagementService.findById(i);
            assertNotNull(existService);
            assertEquals(i, existService.getId());
            assertNotNull(existService.getName());
            assertNotNull(existService.getPrice());
        }
    }

    @Test
    void findById_wrongData_shouldThrowException() {
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.findById(999L));
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.findById(-53L));
        assertThrows(RowNotFoundException.class, () -> serviceManagementService.findById(0L));
    }

    @Test
    void deleteById_validData_shouldPass() throws RowNotFoundException, ReferenceRestrictionException {
        ServiceDto serviceDto = ServiceDto.builder()
                .id(1L)
                .build();
        serviceManagementService.deleteById(serviceDto.getId());
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
        assertThrows(ReferenceRestrictionException.class, () -> serviceManagementService.deleteById(ReadPersonDto.builder()
                .id(2L)
                .build().getId()));
        assertThrows(ReferenceRestrictionException.class, () -> serviceManagementService.deleteById(ReadPersonDto.builder()
                .id(3L)
                .build().getId()));
    }
}
