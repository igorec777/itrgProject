package app.converter.person;

import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.entity.Person;

public interface PersonConverter {

    ReadPersonDto toReadPersonDto(Person entity);

    Person fromCreateUpdatePersonDto(CreateUpdatePersonDto dto);
}
