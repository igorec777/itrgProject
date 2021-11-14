package converter.person.impl;

import converter.person.CreateUpdatePersonConverter;
import dto.person.CreateUpdatePersonDto;
import entity.Person;
import org.springframework.stereotype.Component;

@Component
public class CreateUpdatePersonConverterImpl implements CreateUpdatePersonConverter {

    @Override
    public Person fromDto(CreateUpdatePersonDto dto) {
        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .build();
    }
}
