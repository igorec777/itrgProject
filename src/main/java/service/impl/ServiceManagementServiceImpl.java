package service.impl;

import converter.service.ServiceConverter;
import converter.service.impl.ServiceConverterImpl;
import dao.RecordDao;
import dao.ServiceDao;
import dto.service.ServiceDto;
import entity.Person;
import entity.Record;
import entity.Role;
import entity.Service;
import exceptions.ReferenceRestrictionException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;
import exceptions.UniqueRestrictionException;
import org.springframework.beans.factory.annotation.Autowired;
import service.ServiceManagementService;

import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceManagementServiceImpl implements ServiceManagementService {

    private final ServiceDao serviceDao;
    private final RecordDao recordDao;
    private final ServiceConverter serviceConverter;

    @Autowired
    ServiceManagementServiceImpl(ServiceDao serviceDao, RecordDao recordDao, ServiceConverterImpl serviceConverter) {
        this.serviceDao = serviceDao;
        this.recordDao = recordDao;
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
    public ServiceDto create(ServiceDto serviceDto) throws UnavailableObjectException, UniqueRestrictionException {
        if (serviceDto == null) {
            String exMessage = "'serviceDto' is unavailable";
            System.out.println("Log: " + exMessage);
            throw new UnavailableObjectException(exMessage);
        }
        if (!isNameUnique(serviceDto.getName())) {
            String exMessage = Service.class.getSimpleName() + " with name:" + serviceDto.getName() +
                    " already exist";
            System.out.println("Log: " + exMessage);
            throw new UniqueRestrictionException(exMessage);
        }
        Service newService = serviceConverter.fromDto(serviceDto);
        return serviceConverter.toDto(serviceDao.create(newService));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException {
        Service existService = serviceDao.findById(id);
        Set<Record> existRecords = recordDao.findAll();
        boolean isAnyRecordHasService = existRecords.stream()
                .anyMatch(r -> r.getService().getId().equals(id));

        if (isAnyRecordHasService) {
            String exMessage = "Cannot delete " + Service.class.getSimpleName() + " with id: " + id + " because" +
                    " some " + Record.class.getSimpleName() + "(s) references to this " + Service.class.getSimpleName();
            System.out.println("Log: " + exMessage);
            throw new ReferenceRestrictionException(exMessage);
        }
        serviceDao.delete(existService);
    }

    private boolean isNameUnique(String name) {
        Set<Service> services = serviceDao.findAll();
        return services.stream()
                .noneMatch(s -> name.equals(s.getName()));
    }
}
