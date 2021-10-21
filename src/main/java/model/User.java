package model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    private int Id;
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String passwordHash;
}
