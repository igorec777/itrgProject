package repository;

import entity.Service;

import java.util.List;

public interface ServiceRepository {
    Service create(Service service);

    Service readById(int id);

    List<Service> readAll();

    int updateById(int id, Service service);

    int deleteById(int id);
}
