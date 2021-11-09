package dto.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdatePersonDto {

    private Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
}
