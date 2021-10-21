package model;

import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@RequiredArgsConstructor
public class Record {

    private int id;
    private final Date date;
    private final Timestamp startTime;
    private final Timestamp endTime;
}
