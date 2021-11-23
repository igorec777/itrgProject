package app.controller;

import app.dto.role.RoleDto;
import app.entity.Role;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;
import app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}")
    RoleDto findById(@PathVariable Long id) throws RowNotFoundException {
        return roleService.findById(id);
    }

    @PostMapping("/add")
    RoleDto create(@RequestBody RoleDto newRole) throws UniqueRestrictionException,
            RowNotFoundException, UnavailableObjectException {
        return roleService.create(newRole);
    }

}
