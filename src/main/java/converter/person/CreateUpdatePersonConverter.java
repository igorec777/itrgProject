package converter.person;

import dto.person.CreateUpdatePersonDto;
import dto.person.ReadPersonDto;
import entity.Person;

public interface CreateUpdatePersonConverter {

    default CreateUpdatePersonDto toDto(Person entity) {
        return null;
    }

    default Person fromDto(CreateUpdatePersonDto dto) {
        return null;
    }
}
