package entity;

import lombok.*;


@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Service {
    private int id;
    private final String name;
    private final double price;
}
