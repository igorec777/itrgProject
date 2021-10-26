package repository.hibernate;

import entity.hibernate.Service;
import org.junit.jupiter.api.Test;
import repository.hibernate.impl.ServiceRepositoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ServiceRepositoryImplTest extends BaseRepositoryTest{

    ServiceRepository serviceRepository;

    ServiceRepositoryImplTest() {
        serviceRepository = new ServiceRepositoryImpl(getSessionFactory());
    }

    @Test
    void readAll_shouldReturnSetOfServices() {
        assertEquals(3, serviceRepository.readAll().size());
    }

    @Test
    void readById_validData_shouldReturnExistService() {
        serviceRepository.readById(1L);
        serviceRepository.readById(2L);
        serviceRepository.readById(3L);
    }

    @Test
    void readById_wrongData_shouldReturnNull() {
        assertNull(serviceRepository.readById(20000L));
        assertNull(serviceRepository.readById(0L));
        assertNull(serviceRepository.readById(-24L));
    }

    @Test
    void create_validData_shouldReturnCreatedService() {
        Service newService = new Service("newService", 114.8);
        assertEquals(newService, serviceRepository.create(newService));
    }

    @Test
    void updateById_validData_shouldPass() {
        serviceRepository.updateById(1L, new Service("newName", 95.2));
    }

    @Test
    void deleteById_validData_shouldReturnNothing() {
        serviceRepository.deleteById(1L);
        serviceRepository.deleteById(2L);
        serviceRepository.deleteById(3L);
    }

    @Test
    void deleteById_wrongData_shouldReturnNothingAndCatchException() {
        serviceRepository.deleteById(-52L);
        serviceRepository.deleteById(0L);
        serviceRepository.deleteById(Long.MAX_VALUE);
    }
}