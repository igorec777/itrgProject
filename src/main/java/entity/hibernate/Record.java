package entity.hibernate;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "record")
public class Record extends BaseEntity<Long> {

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "startTime", nullable = false)
    private Timestamp startTime;

    @Column(name = "endTime", nullable = false)
    private Timestamp endTime;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false)
    private Person client;

    @ManyToOne
    @JoinColumn(name = "workerId", nullable = false)
    private Person worker;

    @ManyToOne
    @JoinColumn(name = "serviceId", nullable = false)
    private Service service;


    public Record(Date date, Timestamp startTime, Timestamp endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
