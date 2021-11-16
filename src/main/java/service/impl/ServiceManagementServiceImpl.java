package service.impl;

import converter.service.ServiceConverter;
import converter.service.impl.ServiceConverterImpl;
import dao.RecordDao;
import dao.ServiceDao;
import dto.service.ServiceDto;
import entity.Record;
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
            throw new UnavailableObjectException("'serviceDto' is unavailable");
        }
        if (!isNameUnique(serviceDto.getName())) {
            throw new UniqueRestrictionException(Service.class.getSimpleName() + " with name:" + serviceDto.getName() +
                    " already exist");
        }
        Service newService = serviceConverter.fromDto(serviceDto);
        return serviceConverter.toDto(serviceDao.create(newService));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException {
        Service existService = serviceDao.findById(id);

        if (isAnyRecordHasService(existService)) {
            throw new ReferenceRestrictionException("Some " + Record.class.getSimpleName()
                    + " references to " + Service.class.getSimpleName() + " with id:" + id);
        }
        serviceDao.delete(existService);
    }

    private boolean isNameUnique(String name) {
        Set<Service> services = serviceDao.findAll();
        return services.stream()
                .noneMatch(s -> name.equals(s.getName()));
    }

    private boolean isAnyRecordHasService(Service service) {
        Set<Record> existRecords = recordDao.findAll();
        return existRecords.stream()
                .anyMatch(r -> service.getId().equals(r.getService().getId()));
    }
}
