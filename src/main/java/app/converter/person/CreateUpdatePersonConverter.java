package app.converter.person;

import app.dto.person.CreateUpdatePersonDto;
import app.entity.Person;

public interface CreateUpdatePersonConverter {

    default CreateUpdatePersonDto toDto(Person entity) {
        return null;
    }

    default Person fromDto(CreateUpdatePersonDto dto) {
        return null;
    }
}
