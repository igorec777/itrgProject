package app.converter.person;

import app.dto.person.CreateUpdatePersonDto;
import app.dto.person.ReadPersonDto;
import app.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonConverterImpl implements PersonConverter {

    @Override
    public ReadPersonDto toReadPersonDto(Person entity) {
        return ReadPersonDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .login(entity.getLogin())
                .roles(entity.getRoles())
                .build();
    }

    @Override
    public Person fromCreateUpdatePersonDto(CreateUpdatePersonDto dto) {
        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .build();
    }
}
