package service;

import dto.service.ServiceDto;
import exceptions.RestrictionViolationException;
import exceptions.RowNotFoundException;

import java.util.Set;

public interface ServiceManagementService {

    ServiceDto findById(Long id) throws RowNotFoundException;

    Set<ServiceDto> findAll();

    ServiceDto create(ServiceDto serviceDto) throws RowNotFoundException;

    void deleteById(Long id) throws RowNotFoundException, RestrictionViolationException;
}
