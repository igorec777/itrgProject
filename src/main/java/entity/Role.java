package entity;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Role {
    private int Id;
    private final String name;
}
