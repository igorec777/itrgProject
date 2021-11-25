package app.controller;

import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.dto.service.ServiceDto;
import app.exception.ReferenceRestrictionException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;
import app.service.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("services")
public class ServiceController {

    @Autowired
    private ServiceManagementService serviceManagementService;

    @GetMapping("/all")
    Set<ServiceDto> findAll() {
        return serviceManagementService.findAll();
    }

    @GetMapping(value = "/get/{id}")
    ServiceDto findById(@PathVariable Long id) throws RowNotFoundException {
        return serviceManagementService.findById(id);
    }

    @PostMapping("/add")
    ServiceDto create(@Valid @RequestBody ServiceDto service) throws UniqueRestrictionException, UnavailableObjectException {
        return serviceManagementService.create(service);
    }

    @DeleteMapping("/delete/{id}")
    HttpStatus deleteById(@PathVariable Long id) throws RowNotFoundException, ReferenceRestrictionException {
        serviceManagementService.deleteById(id);
        return HttpStatus.OK;
    }
}
