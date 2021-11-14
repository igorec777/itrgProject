package entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "record")
public class Record extends BaseEntity<Long> {

    @Column(name = "startTime", nullable = false)
    private Timestamp startTime;

    @Column(name = "endTime", nullable = false)
    private Timestamp endTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId", nullable = false)
    private Person client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workerId", nullable = false)
    private Person worker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceId", nullable = false)
    private Service service;


    @Builder
    public Record(Long id, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
