package repository.jdbc;

import entity.jdbc.Service;
import org.junit.jupiter.api.Test;
import repository.jdbc.impl.ServiceRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ServiceRepositoryImplTest extends BaseRepositoryTest {

    private final ServiceRepository serviceRepository;

    private final List<Service> testServices = List.of(
            new Service(1, "Peeling", 50.4),
            new Service(2, "Sugaring", 40.2),
            new Service(3, "Pedicure", 65.8));

    ServiceRepositoryImplTest() {
        serviceRepository = new ServiceRepositoryImpl(getConnectionPool());
    }

    @Test
    void create() {
        Service newService = new Service("newService", 99.99);
        Service createdService = serviceRepository.create(newService);
        assertEquals(newService.getName(), createdService.getName());
    }

    @Test
    void readById() {
        for (int i = 1; i < testServices.size(); i++) {
            assertEquals(testServices.get(i - 1), serviceRepository.readById(i));
        }
    }

    @Test
    void readAll() {
        List<Service> resultServices = serviceRepository.readAll();

        assertEquals(testServices.size(), resultServices.size());

        for (int i = 0; i < testServices.size(); i++) {
            assertEquals(testServices.get(i), resultServices.get(i));
        }
    }

    @Test
    void updateById() {
        Service updatedService = new Service("newService", 99.99);
        assertEquals(1, serviceRepository.updateById(2, updatedService));
    }

    @Test
    void deleteById() {
        int affectedRows;
        affectedRows = serviceRepository.deleteById(3);
        assertEquals(1, affectedRows);
        affectedRows = serviceRepository.deleteById(-28);
        assertEquals(0, affectedRows);
    }
}