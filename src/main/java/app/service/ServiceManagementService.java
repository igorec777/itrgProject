package app.service;

import app.dto.service.ServiceDto;
import app.exception.ReferenceRestrictionException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;

import java.util.Set;

public interface ServiceManagementService {

    ServiceDto findById(Long id) throws RowNotFoundException;

    Set<ServiceDto> findAll();

    ServiceDto create(ServiceDto serviceDto) throws UnavailableObjectException, UniqueRestrictionException;

    void deleteById(Long id) throws RowNotFoundException, ReferenceRestrictionException;
}
