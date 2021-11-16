package converter.person;

import dto.person.CreateUpdatePersonDto;
import entity.Person;

public interface CreateUpdatePersonConverter {

    default CreateUpdatePersonDto toDto(Person entity) {
        return null;
    }

    default Person fromDto(CreateUpdatePersonDto dto) {
        return null;
    }
}
