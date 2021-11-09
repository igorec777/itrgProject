package dto.person;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReadPersonDto {

    private Long id;
    private String name;
    private String surname;
    private String login;

    private String roles;
}
