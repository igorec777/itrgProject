package entity;

import lombok.*;


@EqualsAndHashCode
@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Person {
    private int Id;
    private final String name;
    private final String surname;
    private final String login;
    private final String password;
}
