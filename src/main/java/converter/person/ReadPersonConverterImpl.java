package converter.person;

import converter.BaseConverter;
import dto.person.ReadPersonDto;
import entity.Person;
import entity.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReadPersonConverterImpl implements BaseConverter<ReadPersonDto, Person> {

    @Override
    public ReadPersonDto toDto(Person entity) {
        String roles = entity.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));
        return ReadPersonDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .login(entity.getLogin())
                .roles(roles)
                .build();
    }
}
