package app.controller;

import app.dto.role.RoleDto;
import app.entity.Role;
import app.exception.ReferenceRestrictionException;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;
import app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    Set<RoleDto> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/get/{id}")
    RoleDto findById(@PathVariable Long id) throws RowNotFoundException {
        return roleService.findById(id);
    }

    @PostMapping("/add")
    RoleDto create(@Valid @RequestBody RoleDto role) throws UniqueRestrictionException,
            RowNotFoundException, UnavailableObjectException {
        return roleService.create(role);
    }

    @DeleteMapping("/delete/{id}")
    HttpStatus deleteById(@PathVariable Long id) throws RowNotFoundException, ReferenceRestrictionException {
        roleService.deleteById(id);
        return HttpStatus.OK;
    }



}
