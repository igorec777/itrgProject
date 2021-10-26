package entity.jdbc;


import lombok.*;

import java.sql.Time;
import java.sql.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Record {
    private int Id;
    private final Date date;
    private final Time startTime;
    private final Time endTime;
    private final Person client;
    private final Person worker;
    private final Service service;
}
