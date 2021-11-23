package app.converter.person;

import app.dto.person.ReadPersonDto;
import app.entity.Person;

public interface ReadPersonConverter {

    default ReadPersonDto toDto(Person entity) {
        return null;
    }

    default Person fromDto(ReadPersonConverter dto) {
        return null;
    }
}
