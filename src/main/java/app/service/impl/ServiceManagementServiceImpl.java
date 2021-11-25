package app.service.impl;

import app.converter.service.ServiceConverter;
import app.converter.service.ServiceConverterImpl;
import app.dao.RecordDao;
import app.dao.ServiceDao;
import app.dto.service.ServiceDto;
import app.entity.Record;
import app.entity.Service;
import app.exception.ReferenceRestrictionException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;
import app.service.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;

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
        return serviceConverter.toServiceDto(serviceDao.findById(id));
    }

    @Override
    public Set<ServiceDto> findAll() {
        return serviceDao.findAll().stream()
                .map(serviceConverter::toServiceDto)
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
        Service newService = serviceConverter.fromServiceDto(serviceDto);
        return serviceConverter.toServiceDto(serviceDao.create(newService));
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
