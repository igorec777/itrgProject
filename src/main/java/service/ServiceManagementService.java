package service;

import dto.service.ServiceDto;
import exceptions.ReferenceRestrictionException;
import exceptions.RowNotFoundException;
import exceptions.UnavailableObjectException;
import exceptions.UniqueRestrictionException;

import java.util.Set;

public interface ServiceManagementService {

    ServiceDto findById(Long id) throws RowNotFoundException;

    Set<ServiceDto> findAll();

    ServiceDto create(ServiceDto serviceDto) throws UnavailableObjectException, UniqueRestrictionException;

    //todo
    void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException;
}
