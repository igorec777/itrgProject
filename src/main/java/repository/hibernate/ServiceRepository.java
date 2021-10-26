package repository.hibernate;

import entity.hibernate.Service;

import java.util.Set;

public interface ServiceRepository {
    Set<Service> readAll();

    Service readById(Long id);

    Service create(Service service);

    void deleteById(Long id);

    void updateById(Long id, Service service);
}
