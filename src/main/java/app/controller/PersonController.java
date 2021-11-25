package app.controller;

import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.entity.Person;
import app.exception.*;
import app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("people")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/all")
    Set<ReadPersonDto> findAll() {
        return personService.findAll();
    }

    @GetMapping(value = "/get/{id}")
    ReadPersonDto findById(@PathVariable Long id) throws RowNotFoundException {
        return personService.findById(id);
    }

    @PostMapping("/add")
    ReadPersonDto create(@Valid @RequestBody CreateUpdatePersonDto person) throws RowNotFoundException,
            UniqueRestrictionException, UnavailableObjectException {
        return personService.create(person);
    }

    @DeleteMapping("/delete/{id}")
    HttpStatus deleteById(@PathVariable Long id) throws RowNotFoundException, ReferenceRestrictionException {
        personService.deleteById(id);
        return HttpStatus.OK;
    }

    @PutMapping("/update")
    HttpStatus update(@Valid @RequestBody CreateUpdatePersonDto person) throws UniqueRestrictionException, RowNotFoundException {
        personService.update(person);
        return HttpStatus.OK;
    }

    @PutMapping("/setRole")
    HttpStatus setRole(@RequestParam Long personId, @RequestParam Long roleId) throws RowNotFoundException, UnableToUpdateException {
        personService.setRole(personId, roleId);
        return HttpStatus.OK;
    }

    @PutMapping("/removeRole")
    HttpStatus removeRole(@RequestParam Long personId, @RequestParam Long roleId) throws RowNotFoundException, UnableToUpdateException {
        personService.removeRole(personId, roleId);
        return HttpStatus.OK;
    }

}
