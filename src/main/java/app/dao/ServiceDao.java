package app.dao;

import app.entity.Service;
import app.exception.RowNotFoundException;

import java.util.Set;

public interface ServiceDao {
    Service findById(Long id) throws RowNotFoundException;

    Set<Service> findAll();

    Service create(Service service);

    void delete(Service service);
}
