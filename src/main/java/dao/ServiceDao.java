package dao;

import entity.Service;
import exceptions.RowNotFoundException;

import java.util.Set;

public interface ServiceDao {
    Service findById(Long id) throws RowNotFoundException;

    Set<Service> findAll();

    Service create(Service service);

    void delete(Service service);
}
