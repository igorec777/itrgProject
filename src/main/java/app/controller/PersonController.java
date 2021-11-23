package app.controller;

import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.entity.Person;
import app.exception.RowNotFoundException;
import app.exception.UnavailableObjectException;
import app.exception.UniqueRestrictionException;
import app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}")
    ReadPersonDto findById(@PathVariable Long id) throws RowNotFoundException {
        return personService.findById(id);
    }

    @PostMapping("/add")
    ReadPersonDto create(@RequestBody CreateUpdatePersonDto newPerson) throws RowNotFoundException,
            UniqueRestrictionException, UnavailableObjectException {
        return personService.create(newPerson);
    }

}
