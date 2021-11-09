package converter.person;

import converter.BaseConverter;
import dto.person.CreateUpdatePersonDto;
import entity.Person;
import org.springframework.stereotype.Component;

@Component
public class CreateUpdatePersonConverterImpl implements BaseConverter<CreateUpdatePersonDto, Person> {

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
