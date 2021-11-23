package app.converter.person.impl;

import app.converter.person.ReadPersonConverter;
import app.dto.person.ReadPersonDto;
import app.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class ReadPersonConverterImpl implements ReadPersonConverter {

    @Override
    public ReadPersonDto toDto(Person entity) {
        return ReadPersonDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .login(entity.getLogin())
                .roles(entity.getRoles())
                .build();
    }

}
