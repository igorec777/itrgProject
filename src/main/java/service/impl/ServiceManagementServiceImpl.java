package service.impl;

import converter.service.ServiceConverterImpl;
import dao.RecordDao;
import dao.ServiceDao;
import dto.service.ServiceDto;
import entity.Record;
import entity.Service;
import exceptions.RestrictionViolationException;
import exceptions.RowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import service.ServiceManagementService;

import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceManagementServiceImpl implements ServiceManagementService {

    private final ServiceDao serviceDao;
    private final RecordDao recordDao;
    private final ServiceConverterImpl serviceConverter;

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
    public ServiceDto create(ServiceDto serviceDto) throws RowNotFoundException {
        return serviceConverter.toDto(serviceDao.create(serviceConverter.fromDto(serviceDto)));
    }

    @Override
    public void deleteById(Long id) throws RowNotFoundException, RestrictionViolationException {
        Service existService = serviceDao.findById(id);
        Set<Record> existRecords = recordDao.findAll();
        boolean isAnyRecordHasService = existRecords.stream()
                .anyMatch(r -> r.getService().getId().equals(id));

        if (isAnyRecordHasService) {
            String exMessage = "Cannot delete " + Service.class.getSimpleName() + " with id: " + id + " because" +
                    " some " + Record.class.getSimpleName() + "(s) references to this " + Service.class.getSimpleName();
            System.out.println("Log: " + exMessage);
            throw new RestrictionViolationException(exMessage);
        }
        serviceDao.delete(existService);
    }
}
