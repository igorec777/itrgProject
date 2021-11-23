package app.dto.person;


import app.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReadPersonDto {

    private Long id;
    private String name;
    private String surname;
    private String login;

    private Set<Role> roles;
}
