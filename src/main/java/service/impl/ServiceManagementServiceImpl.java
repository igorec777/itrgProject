package service.impl;

import converter.service.ServiceConverterImpl;
import dao.ServiceDao;
import dto.service.ServiceDto;
import entity.Service;
import exceptions.ConstraintViolationException;
import exceptions.RowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import service.ServiceManagementService;

import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceManagementServiceImpl implements ServiceManagementService {

    private final ServiceDao serviceDao;
    private final ServiceConverterImpl serviceConverter;

    @Autowired
    ServiceManagementServiceImpl(ServiceDao serviceDao, ServiceConverterImpl serviceConverter) {
        this.serviceDao = serviceDao;
        this.serviceConverter = serviceConverter;
    }

    @Override
    public ServiceDto findById(Long id) throws RowNotFoundException {
        return serviceConverter.toDto(serviceDao.findById(id));
    }

    @Override
    public Set<ServiceDto> findAll() {
        return serviceDao.findAll().stream()
                .map(serviceConverter::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ServiceDto create(ServiceDto serviceDto) throws RowNotFoundException {
        return serviceConverter.toDto(serviceDao.create(serviceConverter.fromDto(serviceDto)));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException, ConstraintViolationException {
//        Service existService = serviceDao.findById(id);
//        Set<Service> existServices = serviceDao.findAll();
//        if (isAnyPersonHasRole(existPeople, id)) {
//            String msg = "Cannot delete role with id: " + id + ". Some person references to this role";
//            System.out.println(msg);
//            throw new ConstraintViolationException(msg);
//        }
//        roleDao.delete(existRole);
    }
}
