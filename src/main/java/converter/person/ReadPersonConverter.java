package converter.person;

import dto.person.ReadPersonDto;
import entity.Person;

public interface ReadPersonConverter {

    default ReadPersonDto toDto(Person entity) {
        return null;
    }

    default Person fromDto(ReadPersonConverter dto) {
        return null;
    }
}
